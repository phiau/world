package com.phiau.cache.redis.proxy;

import com.phiau.cache.base.ICachePrimaryKey;
import com.phiau.cache.base.ICacheSerialize;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 20:05
 */
public class CacheRedisMapProxy<V extends ICachePrimaryKey> {

    public long size(BoundHashOperations operations) {
        Long size = operations.size();
        if (null == size) return 0;
        return size;
    }

    public boolean containsKey(BoundHashOperations operations, String key) {
        return operations.hasKey(key);
    }

    public V get(BoundHashOperations operations, String key, ICacheSerialize<V> cacheSerialize) {
        Object object = operations.get(key);
        if (null == object) {
            return null;
        }
        return cacheSerialize.decode((String) object);
    }

    public void put(BoundHashOperations operations, V value, ICacheSerialize<V> cacheSerialize) {
        operations.put(value.primaryKey(), cacheSerialize.encode(value));
    }

    public void remove(BoundHashOperations operations, String key) {
        operations.delete(key);
    }

    public void clear(RedisTemplate redisTemplate, String path) {
        redisTemplate.delete(path);
    }

    public Set<String> keySet(BoundHashOperations operations) {
        return operations.keys();
    }

    public Collection<V> values(BoundHashOperations operations, ICacheSerialize<V> cacheSerialize) {
        return cacheSerialize.string2Entity(operations.values());
    }
}
