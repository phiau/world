package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.base.ICacheMapPrimaryKey;
import com.phiau.cache.core.ICacheMap;
import com.phiau.cache.redis.proxy.CacheRedisMapProxy;
import org.springframework.data.redis.core.BoundHashOperations;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 23:59
 */
public abstract class AbstractCacheRedisMap<V extends ICacheMapPrimaryKey> extends AbstractCacheRedis<V> implements ICacheMap<V> {

    private CacheRedisMapProxy<V> proxy = new CacheRedisMapProxy<>(this);

    @Override
    public long size() {
        return proxy.size(hashOperations());
    }

    @Override
    public boolean isEmpty() {
        return 0 >= size();
    }

    @Override
    public boolean containsKey(Object key) {
        return proxy.containsKey(hashOperations(), key);
    }

    @Override
    public V get(Object key) {
        return proxy.get(hashOperations(), key);
    }

    @Override
    public List<V> multiGet(Collection<?> keys) {
        return proxy.multiGet(hashOperations(), keys);
    }

    @Override
    public void put(V value) {
        proxy.put(hashOperations(), value.primaryKey(), value);
    }

    @Override
    public long remove(Object key) {
        return proxy.remove(hashOperations(), key);
    }

    @Override
    public void clear() {
        proxy.clear(redisTemplate, path());
    }

    @Override
    public Set<String> keySet() {
        return proxy.keySet(hashOperations());
    }

    @Override
    public List<V> values() {
        return proxy.values(hashOperations());
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "map");
    }

    private final BoundHashOperations hashOperations() {
        return redisTemplate.boundHashOps(path());
    }
}
