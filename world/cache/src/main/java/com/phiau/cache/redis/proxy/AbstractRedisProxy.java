package com.phiau.cache.redis.proxy;

import com.phiau.cache.base.ICacheSerialize;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;

/**
 * @author zhenbiao.cai
 * @date 2018/12/14 11:36
 */
public abstract class AbstractRedisProxy<E> implements ICacheSerialize<E> {

    protected ICacheSerialize<E> serialize;

    public AbstractRedisProxy(ICacheSerialize<E> serialize) {
        this.serialize = serialize;
    }

    public void clear(RedisTemplate redisTemplate, String path) {
        redisTemplate.delete(path);
    }

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
