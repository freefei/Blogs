package com.renfei.blog.common.redis.utils;

import com.google.common.base.Charsets;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;

import java.util.UUID;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-11-04
 */
public class DistributedSemaphores {

    /**
     * 获取信号量,因为各客户端的时间可能不一致,因此需要在redis server端设置一个ownerZSet和一个counter来保证获取信号量的先后顺序
     *
     * @param jedisTemplate jedis 连接
     * @param semaphoreName 信号量的名称
     * @param limit         信号量的允许的个数
     * @param timeout       信号量的超时时间,以毫秒计
     * @return 如果获取信号量成功, 则返回生成的信号量的值, 否则返回null
     */
    public static String acquireFairSemaphore(JedisTemplate jedisTemplate, final String semaphoreName,
                                              final int limit, final long timeout) {

        return jedisTemplate.execute(new JedisTemplate.JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                final String identifier = UUID.randomUUID().toString();
                //信号量持有者集合,按照counter的值来排序
                final String ownerZSet = semaphoreName + ":remote:owner";
                final String counterKey = semaphoreName + ":remote:counterKey";
                final long now = System.currentTimeMillis();
                Transaction t = jedis.multi();
                //remove expired semaphore first,注意过期时间是根据semaphoreName set中的score来判断的
                t.zremrangeByScore(semaphoreName.getBytes(Charsets.UTF_8),
                        "-inf".getBytes(Charsets.UTF_8),
                        String.valueOf(now - timeout).getBytes(Charsets.UTF_8));
                //忽略semaphoreName set中的score,只选取ownerZSet中的score
                ZParams params = new ZParams();
                params.weights(1, 0);
                //过期ownerZSet(信号量持有者)中的entry
                t.zinterstore(ownerZSet, params, ownerZSet, semaphoreName);
                Response<Long> c = t.incr(counterKey);
                t.exec();

                //排队尝试获取信号量
                int counter = c.get().intValue();

                t = jedis.multi();
                t.zadd(semaphoreName, now, identifier);
                t.zadd(ownerZSet, counter, identifier);
                Response<Long> r = t.zrank(ownerZSet, identifier);
                t.exec();

                //如果当前还有剩余的信号量,则返回生成的信号量的值
                int rank = r.get().intValue();
                if (rank < limit) {
                    return identifier;
                }

                //如果已经没有信号量了,则删除当前生成的信息,并返回null
                t = jedis.multi();
                t.zrem(semaphoreName, identifier);
                t.zrem(ownerZSet, identifier);
                t.exec();
                return null;

            }
        });
    }

    /**
     * 释放信号量
     *
     * @param jedisTemplate jedis 连接
     * @param semaphoreName 信号量的名称
     * @param identifier    信号量名称对应的值
     * @return 是否释放成功
     */
    public static boolean releaseFairSemaphore(JedisTemplate jedisTemplate, final String semaphoreName, final String identifier) {
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.zrem(semaphoreName, identifier);
                Response<Long> count = t.zrem(semaphoreName + ":remote:owner", identifier);
                t.exec();
                return count.get().intValue() == 1;
            }
        });
    }
}
