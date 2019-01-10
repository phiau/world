package com.phiau.cache.core.prefix;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 19:49
 */
public interface ICacheMapPrefix<V> {

    long size(Object prefixKey);

    boolean isEmpty(Object prefixKey);

    boolean containsKey(Object prefixKey, Object key);

    V get(Object prefixKey, Object key);

    List<V> multiGet(Object prefixKey, Collection<?> keys);

    void put(Object prefixKey, Object primaryKey, V value);

    long remove(Object prefixKey, Object key);

    void clear(Object prefixKey);

    Set<String> keySet(Object prefixKey);

    List<V> values(Object prefixKey);
}
