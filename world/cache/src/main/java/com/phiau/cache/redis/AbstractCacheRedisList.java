package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.ICacheList;
import org.springframework.data.redis.core.BoundListOperations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-27 0:13
 */
public class AbstractCacheRedisList<E> extends AbstractCacheRedis<E> implements ICacheList<E> {

    @Override
    public E get(int index) {
        String s = boundListOps().index(index);
        if (null == s) return null;
        return decode(s);
    }

    @Override
    public void set(int index, E element) {
        boundListOps().set(index, encode(element));
    }

    @Override
    public E remove(int index) {
        E e = get(index);
        if (null != e) {
            remove(e);
        }
        return e;
    }

    @Override
    public boolean remove(E e) {
        return 0 < boundListOps().remove(1, encode(e));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        List<String> sl = boundListOps().range(fromIndex, toIndex);
        if (null == sl) return null;
        List<E> list = new ArrayList<>(sl.size());
        for (String s : sl) {
            list.add(decode(s));
        }
        return list;
    }

    @Override
    public int size() {
        Long size = boundListOps().size();
        return null != size ? (int) (long) size : 0;
    }

    @Override
    public boolean isEmpty() {
        return 0 >= size();
    }

    @Override
    public void add(E e) {
        boundListOps().rightPush(encode(e));
    }

    @Override
    public E getAndRemoveFirst() {
        String s = boundListOps().leftPop();
        if (null == s) return null;
        return decode(s);
    }

    @Override
    public E getAndRemoveLast() {
        String s = boundListOps().rightPop();
        if (null == s) return null;
        return decode(s);
    }

    @Override
    public void clear() {
        redisTemplate.delete(path());
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Redis List can't get iterator");
    }

    private final BoundListOperations<String, String> boundListOps() {
        return redisTemplate.boundListOps(path());
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "list");
    }
}
