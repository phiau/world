package com.phiau.cache.redis.prefix;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.prefix.ICacheMapPrefix;
import com.phiau.cache.redis.AbstractCacheRedis;
import com.phiau.cache.redis.proxy.CacheRedisMapProxy;
import org.springframework.data.redis.core.BoundHashOperations;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 20:02
 */
public abstract class AbstractCacheRedisMapPrefix<V> extends AbstractCacheRedis<V> implements ICacheMapPrefix<V> {

    private CacheRedisMapProxy<V> proxy = new CacheRedisMapProxy<>(this);

    @Override
    public long size(Object prefixKey) {
        return proxy.size(hashOperations(prefixKey));
    }

    @Override
    public boolean isEmpty(Object prefixKey) {
        return 0 >= size(prefixKey);
    }

    @Override
    public boolean containsKey(Object prefixKey, Object key) {
        return proxy.containsKey(hashOperations(prefixKey), key);
    }

    @Override
    public V get(Object prefixKey, Object key) {
        return proxy.get(hashOperations(prefixKey), key);
    }

    @Override
    public List<V> multiGet(Object prefixKey, Collection<?> keys) {
        return proxy.multiGet(hashOperations(prefixKey), keys);
    }

    @Override
    public void put(Object prefixKey, Object primaryKey, V value) {
        proxy.put(hashOperations(prefixKey), primaryKey, value);
    }

    @Override
    public long remove(Object prefixKey, Object key) {
        return proxy.remove(hashOperations(prefixKey), key);
    }

    @Override
    public void clear(Object prefixKey) {
        proxy.clear(redisTemplate, CachePathUtil.cachePath2String(path(), prefixKey));
    }

    @Override
    public Set<String> keySet(Object prefixKey) {
        return proxy.keySet(hashOperations(prefixKey));
    }

    @Override
    public List<V> values(Object prefixKey) {
        return proxy.values(hashOperations(prefixKey));
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "mapPrefix");
    }

    private final BoundHashOperations hashOperations(Object prefixKey) {
        return redisTemplate.boundHashOps(CachePathUtil.cachePath2String(path(), prefixKey));
    }
}
