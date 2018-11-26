package com.phiau.cache.core;

import java.util.List;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 22:05
 */
public interface ICacheList<E> extends ICacheCollection<E> {

    E get(int index);

    long set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    List<E> subList(int fromIndex, int toIndex);
}
