package com.phiau.cache.core;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 22:19
 */
public interface ICacheKeys<V> {

    V get(Object key);

    void set(Object key, V v);

    boolean delete(Object key);

    boolean exists(Object key);

    /**
     * 设置超时时间（单位：秒）
     * @param key
     * @param seconds
     * @return
     */
    boolean expire(Object key, int seconds);

    /**
     * 返回剩余的过期时间（单位：秒）
     * @param key
     * @return
     */
    int ttl(Object key);
}
