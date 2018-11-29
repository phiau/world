package com.phiau.cache.core.prefix;

import java.util.List;

/**
 * @author zhenbiao.cai
 * @date 2018/11/29 16:44
 */
public interface ICacheListPrefix<E> extends ICacheCollectionPrefix<E> {

    E get(String prefixKey, int index);

    void set(String prefixKey, int index, E element);

    E remove(String prefixKey, int index);

    E getAndRemoveFirst(String prefixKey);

    E getAndRemoveLast(String prefixKey);

    List<E> subList(String prefixKey, int fromIndex, int toIndex);
}
