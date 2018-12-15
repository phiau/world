package com.phiau.cache.redis.prefix;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.prefix.ICacheZSetPrefix;
import com.phiau.cache.redis.AbstractCacheRedis;
import com.phiau.cache.redis.proxy.CacheRedisZSetProxy;
import org.springframework.data.redis.core.BoundZSetOperations;

import java.util.Iterator;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/12/15 12:04
 */
public abstract class AbstractCacheRedisZSetPrefix extends AbstractCacheRedis implements ICacheZSetPrefix {

    private CacheRedisZSetProxy proxy = new CacheRedisZSetProxy(this);

    @Override
    public void add(String prefixKey, String key, double score) {
        proxy.add(zSetOperations(prefixKey), key, score);
    }

    @Override
    public long count(String prefixKey, double min, double max) {
        return proxy.count(zSetOperations(prefixKey), min, max);
    }

    @Override
    public double incrementScore(String prefixKey, String key, double delta) {
        return proxy.incrementScore(zSetOperations(prefixKey), key, delta);
    }

    @Override
    public Set<String> range(String prefixKey, long start, long end) {
        return proxy.range(zSetOperations(prefixKey), start, end);
    }

    @Override
    public Set<String> reverseRange(String prefixKey, long start, long end) {
        return proxy.reverseRange(zSetOperations(prefixKey), start, end);
    }

    @Override
    public Set<String> rangeByScore(String prefixKey, double min, double max) {
        return proxy.rangeByScore(zSetOperations(prefixKey), min, max);
    }

    @Override
    public Set<String> reverseRangeByScore(String prefixKey, double min, double max) {
        return proxy.reverseRangeByScore(zSetOperations(prefixKey), min, max);
    }

    @Override
    public long rank(String prefixKey, String key) {
        return proxy.rank(zSetOperations(prefixKey), key);
    }

    @Override
    public long reverseRank(String prefixKey, String key) {
        return proxy.reverseRank(zSetOperations(prefixKey), key);
    }

    @Override
    public void removeRange(String prefixKey, long start, long end) {
        proxy.removeRange(zSetOperations(prefixKey), start, end);
    }

    @Override
    public void removeRangeByScore(String prefixKey, double min, double max) {
        proxy.removeRangeByScore(zSetOperations(prefixKey), min, max);
    }

    @Override
    public double score(String prefixKey, String key) {
        return proxy.score(zSetOperations(prefixKey), key);
    }

    @Override
    public int size(String prefixKey) {
        return (int) proxy.size(zSetOperations(prefixKey));
    }

    @Override
    public boolean isEmpty(String prefixKey) {
        return 0 >= size(prefixKey);
    }

    @Override
    public void add(String prefixKey, Object o) {
        throw new UnsupportedOperationException("Redis ZSet can't add");
    }

    @Override
    public boolean remove(String prefixKey, Object o) {
        return 0 < proxy.remove(zSetOperations(prefixKey), o);
    }

    @Override
    public void clear(String prefixKey) {
        proxy.clear(redisTemplate, path());
    }

    @Override
    public Iterator iterator(String prefixKey) {
        throw new UnsupportedOperationException("Redis List can't get iterator");
    }

    private final BoundZSetOperations<String, String> zSetOperations(String prefix) {
        return redisTemplate.boundZSetOps(CachePathUtil.cachePath2String(path(), prefix));
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "zSetPrefix");
    }
}
