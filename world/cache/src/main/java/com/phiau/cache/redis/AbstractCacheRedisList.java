package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.ICacheList;
import com.phiau.cache.redis.proxy.CacheRedisListProxy;
import org.springframework.data.redis.core.BoundListOperations;

import java.util.Iterator;
import java.util.List;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-27 0:13
 */
public abstract class AbstractCacheRedisList<E> extends AbstractCacheRedis<E> implements ICacheList<E> {

    private CacheRedisListProxy<E> proxy = new CacheRedisListProxy<>(this);

    @Override
    public E get(int index) {
        return proxy.get(boundListOps(), index);
    }

    @Override
    public void set(int index, E element) {
        proxy.set(boundListOps(), index, element);
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
        return proxy.remove(boundListOps(), e);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return proxy.subList(boundListOps(), fromIndex, toIndex);
    }

    @Override
    public int size() {
        return proxy.size(boundListOps());
    }

    @Override
    public boolean isEmpty() {
        return 0 >= size();
    }

    @Override
    public void add(E e) {
        proxy.add(boundListOps(), e);
    }

    @Override
    public E getAndRemoveFirst() {
        return proxy.getAndRemoveFirst(boundListOps());
    }

    @Override
    public E getAndRemoveLast() {
        return proxy.getAndRemoveLast(boundListOps());
    }

    @Override
    public void clear() {
        proxy.clear(redisTemplate, path());
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
