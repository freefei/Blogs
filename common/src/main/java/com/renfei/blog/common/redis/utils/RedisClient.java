package com.renfei.blog.common.redis.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.renfei.blog.common.utils.Paging;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MADE IN IntelliJ IDEA
 * AUTHOR: haolin
 * AT 7/5/14.
 */
public abstract class RedisClient {
    /**
     * list类型key的长度
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key list key
     * @return
     */
    public static Long listLen(final JedisTemplate jedisTemplate, final String key){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    /**
     * 列出key所有的值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key
     * @return
     */
    public static List<Long> listAll2Long(final JedisTemplate jedisTemplate, final String key){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<List<Long>>() {
            @Override
            public List<Long> action(Jedis jedis) {
                List<String> strVals = jedis.lrange(key, 0L, -1L);
                List<Long> longVals = Lists.transform(strVals, new Function<String, Long>() {
                    @Override
                    public Long apply(String strVal) {
                        return Long.valueOf(strVal);
                    }
                });
                return longVals;
            }
        });
    }


    /**
     * list类型key删除一个值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key list类型key
     * @param val 值
     * @return 删除记录数
     */
    public static Long listRemOne(final JedisTemplate jedisTemplate, final String key, final Object val){
        return listRem(jedisTemplate, key, val, 1L);
    }

    /**
     * list类型key删除所有值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key list类型key
     * @param val 值
     * @return 删除记录数
     */
    public static Long listRemAll(final JedisTemplate jedisTemplate, final String key, final Object val){
        return listRem(jedisTemplate, key, val, 0L);
    }

    /**
     * list类型key删除多个值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key list类型key
     * @param val 值
     * @param count
     *  count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     *  count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     *  count = 0 : 移除表中所有与 value 相等的值。
     * @return 删除记录数
     */
    private static Long listRem(final JedisTemplate jedisTemplate, final String key, final Object val, final Long count){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.lrem(key, count, String.valueOf(val));
            }
        });
    }

    /**
     * list类型key列表删除一个值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param keys list类型key
     * @param val 值
     * @return 删除记录数
     */
    public static Long listRemOne(final JedisTemplate jedisTemplate, final List<String> keys, final Object val){
        return listRem(jedisTemplate, keys, val, 1L);
    }

    /**
     * list类型key列表删除所有值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param keys list类型key
     * @param val 值
     * @return 删除记录数
     */
    public static Long listRemAll(final JedisTemplate jedisTemplate, final List<String> keys, final Object val){
        return listRem(jedisTemplate, keys, val, 0L);
    }

    /**
     * list类型key列表删除多个值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param keys list类型key列表
     * @param val 值
     * @param count
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     * @return 删除记录数
     */
    private static Long listRem(final JedisTemplate jedisTemplate, final List<String> keys, final Object val, final Long count){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                Pipeline p = jedis.pipelined();
                Long deleted = 0L;
                for (String key : keys){
                    deleted += p.lrem(key, count, String.valueOf(val)).get();
                }
                p.sync();
                return deleted;
            }
        });
    }

    /**
     * 移除多个key多个值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param keys 多个键
     * @param keyVals 键对应的值列表
     * @return 删除数
     */
    public static Long listRem(final JedisTemplate jedisTemplate, final List<String> keys, final Map<String, List<?>> keyVals){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                Pipeline p = jedis.pipelined();
                Long removed = 0L;
                for (String key : keys){
                    List<?> vals = keyVals.get(key);
                    for (Object val : vals){
                        p.lrem(key, 1L, String.valueOf(val));
                        removed++;
                    }
                }
                p.sync();
                return removed;
            }
        });
    }

    /**
     * 移除单个key多个值
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key 单个键
     * @param vals 值列表
     * @return 删除数
     */
    public static Long listRem(final JedisTemplate jedisTemplate, final String key, final List<?> vals){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                Pipeline p = jedis.pipelined();
                Long removed = 0L;
                for (Object val : vals){
                    p.lrem(key, 1L, String.valueOf(val));
                    removed++;
                }
                p.sync();
                return removed;
            }
        });
    }

    /**
     * 分页获取list类型key的值列表, 从栈顶到栈底
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key list key
     * @param offset 起始偏移
     * @param limit 分页大小
     * @return list类型key的值列表
     */
    public static List<Long> listPaging2Long(final JedisTemplate jedisTemplate,
                                             final String key, final Integer offset, final Integer limit){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<List<Long>>() {
            @Override
            public List<Long> action(Jedis jedis) {
                List<String> ids = jedis.lrange(key, offset, offset + limit - 1);
                return Lists.transform(ids, new Function<String, Long>() {
                    @Override
                    public Long apply(String s) {
                        return s == null ? null : Long.valueOf(s);
                    }
                });
            }
        });
    }

    /**
     * 分页获取list类型key的值分页对象
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key list key
     * @param offset 起始偏移
     * @param limit 分页大小
     * @return list类型key的值分页对象
     */
    public static Paging<Long> listPaging(final JedisTemplate jedisTemplate,
                                             final String key, final Integer offset, final Integer limit){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Paging<Long>>() {
            @Override
            public Paging<Long> action(Jedis jedis) {
                Pipeline pipeline = jedis.pipelined();
                Response<Long> r = pipeline.zcard(key);
                Response<Set<String>> i = pipeline.zrevrange(key, offset, offset + limit - 1);
                pipeline.sync();
                Long total = r.get();
                if (total > 0) {
                    List<Long> ids = Lists.newArrayListWithCapacity(total.intValue());
                    for (String s : i.get()) {
                        ids.add(Long.parseLong(s));
                    }
                    return new Paging<Long>(total, ids);
                }
                return new Paging<Long>(0L, Collections.<Long>emptyList());
            }
        });
    }

    /**
     * 添加值到多个list类型的key中
     * @param jedisTemplate @see com.aixforce.redis.utils.JedisTemplate
     * @param keys list类型key列表
     * @param val 值
     * @return 插入记录数
     */
    public static Long listAdd(final JedisTemplate jedisTemplate, final List<String> keys, final String val){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                Pipeline p = jedis.pipelined();
                Long pushed = 0L;
                for (String key : keys){
                    p.lpush(key, val);
                    ++ pushed;
                }
                p.sync();
                return pushed;
            }
        });
    }

    /**
     * 向Set集合种添加k-v
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key 键
     * @param val 值列表
     * @return 添加记录数
     */
    public static Long setAdd(final JedisTemplate jedisTemplate, final String key, final String val){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                Long inserted = jedis.sadd(key, val);
                return inserted;
            }
        });
    }

    /**
     * 向Set集合种添加k-v
     * @param jedisTemplate @see io.terminus.common.redis.utils.JedisTemplate
     * @param key 键
     * @param vals 值列表
     * @return 添加记录数
     */
    public static Long setAdd(final JedisTemplate jedisTemplate, final String key, final String... vals){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                Long inserted = jedis.sadd(key, vals);
                return inserted;
            }
        });
    }

    /**
     * 获取多个set集合的长度
     * @param jedisTemplate
     * @param keys 键列表
     * @return keys对应的值计数
     */
    public static List<Long> setCounts(final JedisTemplate jedisTemplate, final List<String> keys){
        return jedisTemplate.execute(new JedisTemplate.JedisAction<List<Long>>() {
            @Override
            public List<Long> action(Jedis jedis) {
                Pipeline p = jedis.pipelined();
                List<Response<Long>> resp = Lists.newArrayListWithCapacity(keys.size());
                for (String key : keys){
                    resp.add(p.scard(key));
                }
                p.sync();
                List<Long> counts = Lists.transform(resp, new Function<Response<Long>, Long>() {
                    @Override
                    public Long apply(Response<Long> rl) {
                        return rl.get();
                    }
                });
                return counts;
            }
        });
    }
}
