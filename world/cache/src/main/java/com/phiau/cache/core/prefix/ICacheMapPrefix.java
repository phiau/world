package com.phiau.cache.core.prefix;

import com.phiau.cache.base.ICachePrimaryKeyWithPrefix;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 19:49
 */
public interface ICacheMapPrefix<V extends ICachePrimaryKeyWithPrefix> {

    long size(String prefixKey);

    boolean isEmpty(String prefixKey);

    boolean containsKey(String prefixKey, String key);

    V get(String prefixKey, String key);

    void put(V value);

    void remove(String prefixKey, String key);

    void clear(String prefixKey);

    Set<String> keySet(String prefixKey);

    Collection<V> values(String prefixKey);
}
