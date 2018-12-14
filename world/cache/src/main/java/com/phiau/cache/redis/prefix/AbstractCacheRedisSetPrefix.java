package com.phiau.cache.redis.prefix;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.prefix.ICacheSetPrefix;
import com.phiau.cache.redis.AbstractCacheRedis;
import com.phiau.cache.redis.proxy.CacheRedisSetProxy;
import org.springframework.data.redis.core.BoundSetOperations;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author zhenbiao.cai
 * @date 2018/12/14 11:09
 */
public abstract class AbstractCacheRedisSetPrefix<E> extends AbstractCacheRedis<E> implements ICacheSetPrefix<E> {

    private CacheRedisSetProxy<E> proxy = new CacheRedisSetProxy<>(this);

    @Override
    public Collection<E> all(String prefixKey) {
        return proxy.all(setOperations(prefixKey));
    }

    @Override
    public E randomMember(String prefixKey) {
        return proxy.randomMember(setOperations(prefixKey));
    }

    @Override
    public Collection<E> randomMembers(String prefixKey, int num) {
        return proxy.randomMembers(setOperations(prefixKey), num);
    }

    @Override
    public boolean contains(String prefixKey, E e) {
        return proxy.contains(setOperations(prefixKey), e);
    }

    @Override
    public int size(String prefixKey) {
        return proxy.size(setOperations(prefixKey));
    }

    @Override
    public boolean isEmpty(String prefixKey) {
        return 0 >= size(prefixKey);
    }

    @Override
    public void add(String prefixKey, E e) {
        proxy.add(setOperations(prefixKey), e);
    }

    @Override
    public void clear(String prefixKey) {
        proxy.clear(redisTemplate, CachePathUtil.cachePath2String(path(), prefixKey));
    }

    @Override
    public Iterator<E> iterator(String prefixKey) {
        throw new UnsupportedOperationException("Redis Set can't get iterator");
    }

    @Override
    public boolean remove(String prefixKey, E e) {
        return proxy.remove(setOperations(prefixKey), e);
    }

    private final BoundSetOperations<String, String> setOperations(String prefix) {
        return redisTemplate.boundSetOps(CachePathUtil.cachePath2String(path(), prefix));
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "setPrefix");
    }
}
