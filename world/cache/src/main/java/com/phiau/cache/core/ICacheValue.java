package com.phiau.cache.core;

import java.util.concurrent.TimeUnit;

/**
 * @author zhenbiao.cai
 * @date 2019/1/2 15:22
 */
public interface ICacheValue<V> {

    void delete(String key);

    V get(String key);

    V getAndSet(String key, V value);

    void set(String key, V value);

    void set(String key, V value, long timeout, TimeUnit unit);

    /**
     * 如果绑定键不存在，则将绑定键设置为保留字符串值
     * @param value
     * @return
     */
    boolean setIfAbsent(String key, V value);
}
