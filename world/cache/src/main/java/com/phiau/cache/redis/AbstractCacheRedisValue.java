package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.ICacheValue;
import org.springframework.data.redis.core.BoundValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @author zhenbiao.cai
 * @date 2019/1/2 15:28
 */
public abstract class AbstractCacheRedisValue<V> extends AbstractCacheRedis<V> implements ICacheValue<V> {

    @Override
    public void delete(String key) {
        redisTemplate.delete(CachePathUtil.cachePath2String(path(), key));
    }

    @Override
    public V get(String key) {
        String s = boundValueOps(key).get();
        if (null != s) {
            return decode(s);
        }
        return null;
    }

    @Override
    public V getAndSet(String key, V value) {
        String s = boundValueOps(key).getAndSet(encode(value));
        if (null != s) {
            return decode(s);
        }
        return null;
    }

    @Override
    public void set(String key, V value) {
        boundValueOps(key).set(encode(value));
    }

    @Override
    public void set(String key, V value, long timeout, TimeUnit unit) {
        boundValueOps(key).set(encode(value), timeout, unit);
    }

    @Override
    public boolean setIfAbsent(String key, V value) {
        return boundValueOps(key).setIfAbsent(encode(value));
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "value");
    }

    private final BoundValueOperations<String, String> boundValueOps(String key) {
        return redisTemplate.boundValueOps(CachePathUtil.cachePath2String(path(), key));
    }
}
