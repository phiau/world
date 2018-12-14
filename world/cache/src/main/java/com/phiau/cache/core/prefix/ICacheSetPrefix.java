package com.phiau.cache.core.prefix;

import java.util.Collection;

/**
 * @author zhenbiao.cai
 * @date 2018/12/14 11:06
 */
public interface ICacheSetPrefix<E> extends ICacheCollectionPrefix<E> {

    boolean contains(String prefixKey, E e);

    void add(String prefixKey, E e);

    boolean remove(String prefixKey, E e);

    E randomMember(String prefixKey);

    Collection<E> randomMembers(String prefixKey, int num);

    Collection<E> all(String prefixKey);
}
