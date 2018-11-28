package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.base.ICachePrimaryKey;
import com.phiau.cache.core.ICacheMap;
import com.phiau.cache.redis.proxy.CacheRedisMapProxy;
import org.springframework.data.redis.core.BoundHashOperations;

import java.util.Collection;
import java.util.Set;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 23:59
 */
public class AbstractCacheRedisMap<V extends ICachePrimaryKey> extends AbstractCacheRedis<V> implements ICacheMap<V> {

    private CacheRedisMapProxy<V> proxy = new CacheRedisMapProxy<>();

    @Override
    public long size() {
        return proxy.size(hashOperations());
    }

    @Override
    public boolean isEmpty() {
        return 0 >= size();
    }

    @Override
    public boolean containsKey(String key) {
        return proxy.containsKey(hashOperations(), key);
    }

    @Override
    public V get(String key) {
        return proxy.get(hashOperations(), key, this);
    }

    @Override
    public void put(V value) {
        proxy.put(hashOperations(), value, this);
    }

    @Override
    public void remove(String key) {
        proxy.remove(hashOperations(), key);
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
    public Collection<V> values() {
        return proxy.values(hashOperations(), this);
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "map");
    }

    private final BoundHashOperations hashOperations() {
        return redisTemplate.boundHashOps(path());
    }
}
