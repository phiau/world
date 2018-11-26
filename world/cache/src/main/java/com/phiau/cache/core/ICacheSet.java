package com.phiau.cache.core;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 22:13
 */
public interface ICacheSet<E> extends ICacheCollection<E> {

    boolean add(E e);

    boolean remove(Object o);
}
