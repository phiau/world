package com.phiau.cache.redis.proxy;

import com.phiau.cache.base.ICacheSerialize;
import org.springframework.data.redis.core.BoundSetOperations;

import java.util.Collection;

/**
 * @author zhenbiao.cai
 * @date 2018/12/14 11:21
 */
public class CacheRedisSetProxy<E> extends AbstractRedisProxy<E> {

    public CacheRedisSetProxy(ICacheSerialize<E> serialize) {
        super(serialize);
    }

    public Collection<E> all(BoundSetOperations<String, String> operations) {
        return string2Entity(operations.members());
    }

    public E randomMember(BoundSetOperations<String, String> operations) {
        return decode(operations.randomMember());
    }

    public Collection<E> randomMembers(BoundSetOperations<String, String> operations, int num) {
        return string2Entity(operations.randomMembers(num));
    }

    public boolean contains(BoundSetOperations<String, String> operations, E e) {
        return operations.isMember(encode(e));
    }

    public void add(BoundSetOperations<String, String> operations, E e) {
        operations.add(encode(e));
    }

    public int size(BoundSetOperations<String, String> operations) {
        Long size = operations.size();
        return null != size ? (int) (long) size : 0;
    }

    public boolean remove(BoundSetOperations<String, String> operations, E e) {
        return 0 < operations.remove(encode(e));
    }
}
