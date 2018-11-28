package com.phiau.cache.base;

import java.util.Collection;
import java.util.List;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 21:55
 */
public interface ICacheSerialize<V> {

    List<V> string2Entity(Collection<String> ss);

    String encode(V v);

    V decode(String s);
}
