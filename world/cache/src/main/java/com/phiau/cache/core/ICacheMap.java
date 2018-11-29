package com.phiau.cache.core;

import java.util.Collection;
import java.util.Set;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 21:57
 */
public interface ICacheMap<V> {

    long size();

    boolean isEmpty();

    boolean containsKey(String key);

    V get(String key);

    void put(String primaryKey, V value);

    void remove(String key);

    void clear();

    Set<String> keySet();

    Collection<V> values();
}
