package com.phiau.cache.core;

import java.util.Collection;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 22:13
 */
public interface ICacheSet<E> extends ICacheCollection<E> {

    boolean contains(E e);

    void add(E e);

    boolean remove(E e);

    E randomMember();

    Collection<E> randomMembers(int num);

    Collection<E> all();
}
