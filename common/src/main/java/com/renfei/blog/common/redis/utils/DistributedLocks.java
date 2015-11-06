package com.renfei.blog.common.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-11-04
 */
public class DistributedLocks {
    /**
     * 获取分布式锁
     *
     * @param jedisTemplate  jedis的连接
     * @param lockName       锁的键
     * @param acquireTimeout 最长等待时间,以毫秒为单位
     * @param lockTimeout    锁的最长持有时间,以毫秒为单位
     * @return 如果获取成功, 则返回新生成对应锁键的值, 否则返回null
     */
    public static String acquireLockWithTimeout(JedisTemplate jedisTemplate, String lockName, long acquireTimeout, final int lockTimeout) {
        final String identifier = UUID.randomUUID().toString();
        final String lockKey = "lock:" + lockName;

        long end = System.currentTimeMillis() + acquireTimeout;
        while (System.currentTimeMillis() < end) {
            //判断本次加锁是否成功
            boolean success = jedisTemplate.execute(new JedisTemplate.JedisAction<Boolean>() {
                @Override
                public Boolean action(Jedis jedis) {
                    if (jedis.setnx(lockKey, identifier) == 1) { //如果加锁成功,则加上锁的过期时间,并返回锁的名称
                        jedis.pexpire(lockKey, lockTimeout);
                        return Boolean.TRUE;
                    }
                    //如果没有加锁成功(既锁已经被别的线程持有),则判断是否设置了过期时
                    if (jedis.ttl(lockKey) == -1) {
                        jedis.pexpire(lockKey, lockTimeout);
                    }
                    return Boolean.FALSE;
                }
            });

            if (success) {//加锁成功则返回新生成的对应锁键的值
                return identifier;
            }

            //如果未加锁成功,则 sleep 1毫秒之后再重试
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // null indicates that the lock was not acquired
        return null;
    }

    /**
     * 释放锁
     *
     * @param jedisTemplate jedis的连接
     * @param lockName      锁的键
     * @param identifier    锁的值
     * @return 是否释放成功
     */
    public static boolean releaseLock(JedisTemplate jedisTemplate, final String lockName, final String identifier) {
        final String lockKey = "lock:" + lockName;

        return jedisTemplate.execute(new JedisTemplate.JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                while (true) {
                    jedis.watch(lockKey);
                    //如果锁没有过期,则锁的值必然没有改变
                    if (identifier!=null && identifier.equals( jedis.get(lockKey))) {
                        Transaction t = jedis.multi();
                        t.del(lockKey);
                        List<Object> results = t.exec();
                        if (results == null) {
                            continue;
                        }
                        return true;
                    }
                    //锁已经过期了,可以跳出了
                    jedis.unwatch();
                    break;
                }
                return false;
            }
        });
    }
}
