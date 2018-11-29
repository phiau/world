package com.phiau.cache.redis.proxy;

import com.phiau.cache.base.ICacheSerialize;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;

/**
 * @author zhenbiao.cai
 * @date 2018/11/29 16:50
 */
public class CacheRedisListProxy<E> implements ICacheSerialize<E> {

    private ICacheSerialize<E> serialize;

    public CacheRedisListProxy(ICacheSerialize<E> serialize) {
        this.serialize = serialize;
    }

    public E get(BoundListOperations operations, int index) {
        return decode((String) operations.index(index));
    }

    public void set(BoundListOperations operations, int index, E element) {
        operations.set(index, encode(element));
    }

    public boolean remove(BoundListOperations operations, E e) {
        return 0 < operations.remove(1, encode(e));
    }

    public List<E> subList(BoundListOperations operations, int fromIndex, int toIndex) {
        return string2Entity(operations.range(fromIndex, toIndex));
    }

    public int size(BoundListOperations operations) {
        Long size = operations.size();
        return null != size ? (int) (long) size : 0;
    }

    public void add(BoundListOperations operations, E e) {
        operations.rightPush(encode(e));
    }

    public E getAndRemoveFirst(BoundListOperations operations) {
        return decode((String) operations.leftPop());
    }

    public E getAndRemoveLast(BoundListOperations operations) {
        return decode((String) operations.rightPop());
    }

    public void clear(RedisTemplate redisTemplate, String path) {
        redisTemplate.delete(path);
    }

    /** ICacheSerialize proxy */

    @Override
    public List<E> string2Entity(Collection<String> ss) {
        return serialize.string2Entity(ss);
    }

    @Override
    public String encode(E e) {
        return serialize.encode(e);
    }

    @Override
    public E decode(String s) {
        return serialize.decode(s);
    }
}
