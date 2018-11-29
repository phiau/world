package com.phiau.cache.redis.proxy;

import com.phiau.cache.base.ICacheSerialize;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 20:05
 */
public class CacheRedisMapProxy<V> implements ICacheSerialize<V> {

    private ICacheSerialize<V> serialize;

    public CacheRedisMapProxy(ICacheSerialize<V> serialize) {
        this.serialize = serialize;
    }

    public long size(BoundHashOperations operations) {
        Long size = operations.size();
        if (null == size) return 0;
        return size;
    }

    public boolean containsKey(BoundHashOperations operations, String key) {
        return operations.hasKey(key);
    }

    public V get(BoundHashOperations operations, String key) {
        Object object = operations.get(key);
        if (null == object) {
            return null;
        }
        return decode((String) object);
    }

    public void put(BoundHashOperations operations, String primaryKey, V value) {
        operations.put(primaryKey, encode(value));
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

    public Collection<V> values(BoundHashOperations operations) {
        return string2Entity(operations.values());
    }

    /** ICacheSerialize proxy */

    @Override
    public List<V> string2Entity(Collection<String> ss) {
        return serialize.string2Entity(ss);
    }

    @Override
    public String encode(V v) {
        return serialize.encode(v);
    }

    @Override
    public V decode(String s) {
        return serialize.decode(s);
    }
}
