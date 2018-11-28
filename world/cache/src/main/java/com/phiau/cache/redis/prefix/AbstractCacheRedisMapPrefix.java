package com.phiau.cache.redis.prefix;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.base.ICachePrimaryKeyWithPrefix;
import com.phiau.cache.core.prefix.ICacheMapPrefix;
import com.phiau.cache.redis.AbstractCacheRedis;
import com.phiau.cache.redis.proxy.CacheRedisMapProxy;
import org.springframework.data.redis.core.BoundHashOperations;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 20:02
 */
public class AbstractCacheRedisMapPrefix<V extends ICachePrimaryKeyWithPrefix> extends AbstractCacheRedis<V> implements ICacheMapPrefix<V> {

    private CacheRedisMapProxy<V> proxy = new CacheRedisMapProxy<>();

    @Override
    public long size(String prefixKey) {
        return proxy.size(hashOperations(prefixKey));
    }

    @Override
    public boolean isEmpty(String prefixKey) {
        return 0 >= size(prefixKey);
    }

    @Override
    public boolean containsKey(String prefixKey, String key) {
        return proxy.containsKey(hashOperations(prefixKey), key);
    }

    @Override
    public V get(String prefixKey, String key) {
        return proxy.get(hashOperations(prefixKey), key, this);
    }

    @Override
    public void put(V value) {
        proxy.put(hashOperations(value.prefixKey()), value, this);
    }

    @Override
    public void remove(String prefixKey, String key) {
        proxy.remove(hashOperations(prefixKey), key);
    }

    @Override
    public void clear(String prefixKey) {
        proxy.clear(redisTemplate, CachePathUtil.cachePath2String(path(), prefixKey));
    }

    @Override
    public Set<String> keySet(String prefixKey) {
        return proxy.keySet(hashOperations(prefixKey));
    }

    @Override
    public Collection<V> values(String prefixKey) {
        return proxy.values(hashOperations(prefixKey), this);
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "mapPrefix");
    }

    private final BoundHashOperations hashOperations(String prefix) {
        return redisTemplate.boundHashOps(CachePathUtil.cachePath2String(path(), prefix));
    }
}
