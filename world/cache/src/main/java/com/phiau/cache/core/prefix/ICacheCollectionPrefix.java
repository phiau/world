package com.phiau.cache.core.prefix;

import java.util.Iterator;

/**
 * @author zhenbiao.cai
 * @date 2018/11/29 16:45
 */
public interface ICacheCollectionPrefix<E> {

    int size(String prefixKey);

    boolean isEmpty(String prefixKey);

    void add(String prefixKey, E e);

    /** 使用这个必须确保内存数据和缓存的一致 */
    boolean remove(String prefixKey, E e);

    void clear(String prefixKey);

    Iterator<E> iterator(String prefixKey);
}
