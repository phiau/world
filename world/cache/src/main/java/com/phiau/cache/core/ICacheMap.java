package com.phiau.cache.core;

import com.phiau.cache.base.ICachePrimaryKey;

import java.util.Collection;
import java.util.Set;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 21:57
 */
public interface ICacheMap<V extends ICachePrimaryKey> {

    long size();

    boolean isEmpty();

    boolean containsKey(String key);

    V get(String key);

    void put(V value);

    void remove(String key);

    void clear();

    Set<String> keySet();

    Collection<V> values();
}
