package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.ICacheSet;
import org.springframework.data.redis.core.BoundSetOperations;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author zhenbiao.cai
 * @date 2018/11/27 16:38
 */
public class AbstractCacheRedisSet<E> extends AbstractCacheRedis<E> implements ICacheSet<E> {

    @Override
    public Collection<E> all() {
        return string2Entity(setOperations().members());
    }

    @Override
    public E randomMember() {
        return decode(setOperations().randomMember());
    }

    @Override
    public Collection<E> randomMembers(int num) {
        return string2Entity(setOperations().randomMembers(num));
    }

    @Override
    public boolean contains(E e) {
        return setOperations().isMember(encode(e));
    }

    @Override
    public int size() {
        Long size = setOperations().size();
        return null != size ? (int) (long) size : 0;
    }

    @Override
    public boolean isEmpty() {
        return 0 >= size();
    }

    @Override
    public void add(E e) {
        setOperations().add(encode(e));
    }

    @Override
    public void clear() {
        redisTemplate.delete(path());
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Redis Set can't get iterator");
    }

    @Override
    public boolean remove(E e) {
        return 0 < setOperations().remove(encode(e));
    }

    private final BoundSetOperations<String, String> setOperations() {
        return redisTemplate.boundSetOps(path());
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "set");
    }
}
