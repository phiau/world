package com.phiau.cache.core.prefix;

import java.util.List;

/**
 * @author zhenbiao.cai
 * @date 2018/11/29 16:44
 */
public interface ICacheListPrefix<E> extends ICacheCollectionPrefix<E> {

    E get(Object prefixKey, int index);

    void set(Object prefixKey, int index, E element);

    E removeByIndex(Object prefixKey, int index);

    E getAndRemoveFirst(Object prefixKey);

    E getAndRemoveLast(Object prefixKey);

    List<E> subList(Object prefixKey, int fromIndex, int toIndex);
}
