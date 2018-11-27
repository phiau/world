package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.base.ICachePrimaryKey;
import com.phiau.cache.core.ICacheMap;
import org.springframework.data.redis.core.BoundHashOperations;

import java.util.Collection;
import java.util.Set;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 23:59
 */
public class AbstractCacheRedisMap<V extends ICachePrimaryKey> extends AbstractCacheRedis<V> implements ICacheMap<V> {

    @Override
    public long size() {
        Long size = boundHashOperations().size();
        if (null == size) return 0;
        return size;
    }

    @Override
    public boolean isEmpty() {
        return 0 >= size();
    }

    @Override
    public boolean containsKey(String key) {
        return boundHashOperations().hasKey(key);
    }

    @Override
    public V get(String key) {
        Object object = boundHashOperations().get(key);
        if (null == object) {
            return null;
        }
        return decode((String) object);
    }

    @Override
    public void put(V value) {
        boundHashOperations().put(value.primaryKey(), encode(value));
    }

    @Override
    public void remove(String key) {
        boundHashOperations().delete(key);
    }

    @Override
    public void clear() {
        redisTemplate.delete(path());
    }

    @Override
    public Set<String> keySet() {
        return boundHashOperations().keys();
    }

    @Override
    public Collection<V> values() {
        return string2Entity(boundHashOperations().values());
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "map");
    }

    private final BoundHashOperations boundHashOperations() {
        return redisTemplate.boundHashOps(path());
    }
}
