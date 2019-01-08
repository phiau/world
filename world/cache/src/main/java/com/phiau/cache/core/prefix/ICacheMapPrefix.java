package com.phiau.cache.core.prefix;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 19:49
 */
public interface ICacheMapPrefix<V> {

    long size(Object prefixKey);

    boolean isEmpty(Object prefixKey);

    boolean containsKey(Object prefixKey, String key);

    V get(Object prefixKey, String key);

    void put(Object prefixKey, String primaryKey, V value);

    void remove(Object prefixKey, String key);

    void clear(Object prefixKey);

    Set<String> keySet(Object prefixKey);

    Collection<V> values(Object prefixKey);
}
