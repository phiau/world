package com.phiau.cache.redis.proxy;

import com.phiau.cache.base.ICacheSerialize;
import org.springframework.data.redis.core.BoundHashOperations;

import java.util.Collection;
import java.util.List;
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

    public boolean containsKey(BoundHashOperations operations, Object key) {
        return operations.hasKey(key2String(key));
    }

    public E get(BoundHashOperations operations, Object key) {
        Object object = operations.get(key2String(key));
        if (null == object) {
            return null;
        }
        return decode((String) object);
    }

    public List<E> multiGet(BoundHashOperations operations, Collection<?> keys) {
        List<String> list = operations.multiGet(key2String(keys));
        if (null != list && 0 < list.size()) {
            return string2Entity(list);
        }
        return null;
    }

    public void put(BoundHashOperations operations, Object primaryKey, E value) {
        operations.put(key2String(primaryKey), encode(value));
    }

    public long remove(BoundHashOperations operations, Object key) {
        return operations.delete(key2String(key));
    }

    public Set<String> keySet(BoundHashOperations operations) {
        return operations.keys();
    }

    public List<E> values(BoundHashOperations operations) {
        return string2Entity(operations.values());
    }
}
