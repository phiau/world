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
    public E get(Object prefixKey, int index) {
        return proxy.get(boundListOps(prefixKey), index);
    }

    @Override
    public void set(Object prefixKey, int index, E element) {
        proxy.set(boundListOps(prefixKey), index, element);
    }

    @Override
    public E removeByIndex(Object prefixKey, int index) {
        E e = get(prefixKey, index);
        if (null != e) {
            remove(prefixKey, e);
        }
        return e;
    }

    @Override
    public boolean remove(Object prefixKey, E e) {
        return proxy.remove(boundListOps(prefixKey), e);
    }

    @Override
    public List<E> subList(Object prefixKey, int fromIndex, int toIndex) {
        return proxy.subList(boundListOps(prefixKey), fromIndex, toIndex);
    }

    @Override
    public int size(Object prefixKey) {
        return proxy.size(boundListOps(prefixKey));
    }

    @Override
    public boolean isEmpty(Object prefixKey) {
        return 0 >= size(prefixKey);
    }

    @Override
    public void add(Object prefixKey, E e) {
        proxy.add(boundListOps(prefixKey), e);
    }

    @Override
    public E getAndRemoveFirst(Object prefixKey) {
        return proxy.getAndRemoveFirst(boundListOps(prefixKey));
    }

    @Override
    public E getAndRemoveLast(Object prefixKey) {
        return proxy.getAndRemoveLast(boundListOps(prefixKey));
    }

    @Override
    public void clear(Object prefixKey) {
        proxy.clear(redisTemplate, CachePathUtil.cachePath2String(path(), prefixKey));
    }

    @Override
    public Iterator<E> iterator(Object prefixKey) {
        throw new UnsupportedOperationException("Redis List can't get iterator");
    }

    private final BoundListOperations boundListOps(Object prefixKey) {
        return redisTemplate.boundListOps(CachePathUtil.cachePath2String(path(), prefixKey));
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "listPrefix");
    }
}
