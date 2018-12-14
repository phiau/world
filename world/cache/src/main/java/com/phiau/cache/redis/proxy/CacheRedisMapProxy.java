package com.phiau.cache.redis.proxy;

import com.phiau.cache.base.ICacheSerialize;
import org.springframework.data.redis.core.BoundHashOperations;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 20:05
 */
public class CacheRedisMapProxy<E> extends AbstractRedisProxy<E> {

    public CacheRedisMapProxy(ICacheSerialize<E> serialize) {
        super(serialize);
    }

    public long size(BoundHashOperations operations) {
        Long size = operations.size();
        if (null == size) return 0;
        return size;
    }

    public boolean containsKey(BoundHashOperations operations, String key) {
        return operations.hasKey(key);
    }

    public E get(BoundHashOperations operations, String key) {
        Object object = operations.get(key);
        if (null == object) {
            return null;
        }
        return decode((String) object);
    }

    public void put(BoundHashOperations operations, String primaryKey, E value) {
        operations.put(primaryKey, encode(value));
    }

    public void remove(BoundHashOperations operations, String key) {
        operations.delete(key);
    }

    public Set<String> keySet(BoundHashOperations operations) {
        return operations.keys();
    }

    public Collection<E> values(BoundHashOperations operations) {
        return string2Entity(operations.values());
    }
}
