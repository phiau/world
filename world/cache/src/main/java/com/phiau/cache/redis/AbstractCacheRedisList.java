package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.ICacheList;
import org.springframework.data.redis.core.BoundListOperations;

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
    public long set(int index, E element) {
        return boundListOps().leftPush(encode(element));
    }

    @Override
    public void add(int index, E element) {
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    private final BoundListOperations<String, String> boundListOps() {
        return redisTemplate.boundListOps(path());
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "list");
    }
}
