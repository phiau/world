package com.phiau.cache.redis.prefix;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.prefix.ICacheListPrefix;
import com.phiau.cache.redis.AbstractCacheRedis;
import com.phiau.cache.redis.proxy.CacheRedisListProxy;
import org.springframework.data.redis.core.BoundListOperations;

import java.util.Iterator;
import java.util.List;

/**
 * @author zhenbiao.cai
 * @date 2018/11/29 16:47
 */
public abstract class AbstractCacheRedisListPrefix<E> extends AbstractCacheRedis<E> implements ICacheListPrefix<E> {

    private CacheRedisListProxy<E> proxy = new CacheRedisListProxy<>(this);

    @Override
    public E get(String prefixKey, int index) {
        return proxy.get(boundListOps(prefixKey), index);
    }

    @Override
    public void set(String prefixKey, int index, E element) {
        proxy.set(boundListOps(prefixKey), index, element);
    }

    @Override
    public E remove(String prefixKey, int index) {
        E e = get(prefixKey, index);
        if (null != e) {
            remove(prefixKey, e);
        }
        return e;
    }

    @Override
    public boolean remove(String prefixKey, E e) {
        return proxy.remove(boundListOps(prefixKey), e);
    }

    @Override
    public List<E> subList(String prefixKey, int fromIndex, int toIndex) {
        return proxy.subList(boundListOps(prefixKey), fromIndex, toIndex);
    }

    @Override
    public int size(String prefixKey) {
        return proxy.size(boundListOps(prefixKey));
    }

    @Override
    public boolean isEmpty(String prefixKey) {
        return 0 >= size(prefixKey);
    }

    @Override
    public void add(String prefixKey, E e) {
        proxy.add(boundListOps(prefixKey), e);
    }

    @Override
    public E getAndRemoveFirst(String prefixKey) {
        return proxy.getAndRemoveFirst(boundListOps(prefixKey));
    }

    @Override
    public E getAndRemoveLast(String prefixKey) {
        return proxy.getAndRemoveLast(boundListOps(prefixKey));
    }

    @Override
    public void clear(String prefixKey) {
        proxy.clear(redisTemplate, CachePathUtil.cachePath2String(path(), prefixKey));
    }

    @Override
    public Iterator<E> iterator(String prefixKey) {
        throw new UnsupportedOperationException("Redis List can't get iterator");
    }

    private final BoundListOperations boundListOps(String prefix) {
        return redisTemplate.boundListOps(CachePathUtil.cachePath2String(path(), prefix));
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "listPrefix");
    }
}
