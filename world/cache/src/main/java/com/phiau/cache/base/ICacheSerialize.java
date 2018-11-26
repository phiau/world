package com.phiau.cache.base;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 21:55
 */
public interface ICacheSerialize<V> {

    String encode(V v);

    V decode(String s);
}
