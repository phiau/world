package com.phiau.cache.core;

import java.util.Iterator;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 22:05
 */
public interface ICacheCollection<E> {

    int size();

    boolean isEmpty();

    boolean add(E e);

    boolean remove(Object o);

    void clear();

    Iterator<E> iterator();
}
