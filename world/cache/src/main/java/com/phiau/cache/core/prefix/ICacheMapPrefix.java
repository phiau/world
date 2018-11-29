package com.phiau.cache.core.prefix;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 19:49
 */
public interface ICacheMapPrefix<V> {

    long size(String prefixKey);

    boolean isEmpty(String prefixKey);

    boolean containsKey(String prefixKey, String key);

    V get(String prefixKey, String key);

    void put(String prefixKey, String primaryKey, V value);

    void remove(String prefixKey, String key);

    void clear(String prefixKey);

    Set<String> keySet(String prefixKey);

    Collection<V> values(String prefixKey);
}
