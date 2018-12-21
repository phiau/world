package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.ICacheZSet;
import com.phiau.cache.redis.proxy.CacheRedisZSetProxy;
import org.springframework.data.redis.core.BoundZSetOperations;

import java.util.Iterator;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/12/15 11:57
 */
public abstract class AbstractCacheRedisZSet extends AbstractCacheRedis implements ICacheZSet {

    private CacheRedisZSetProxy proxy = new CacheRedisZSetProxy(this);

    @Override
    public void add(String key, double score) {
        proxy.add(zSetOperations(), key, score);
    }

    @Override
    public long count(double min, double max) {
        return proxy.count(zSetOperations(), min, max);
    }

    @Override
    public double incrementScore(String key, double delta) {
        return proxy.incrementScore(zSetOperations(), key, delta);
    }

    @Override
    public String range(long rank) {
        Set<String> set = range(rank, rank);
        if (null != set && 0 < set.size()) {
            return set.iterator().next();
        }
        return null;
    }

    @Override
    public Set<String> range(long start, long end) {
        return proxy.range(zSetOperations(), start, end);
    }

    @Override
    public String reverseRange(long rank) {
        Set<String> set = reverseRange(rank, rank);
        if (null != set && 0 < set.size()) {
            return set.iterator().next();
        }
        return null;
    }

    @Override
    public Set<String> reverseRange(long start, long end) {
        return proxy.reverseRange(zSetOperations(), start, end);
    }

    @Override
    public Set<String> rangeByScore(double min, double max) {
        return proxy.rangeByScore(zSetOperations(), min, max);
    }

    @Override
    public Set<String> reverseRangeByScore(double min, double max) {
        return proxy.reverseRangeByScore(zSetOperations(), min, max);
    }

    @Override
    public long rank(String key) {
        return proxy.rank(zSetOperations(), key);
    }

    @Override
    public long reverseRank(String key) {
        return proxy.reverseRank(zSetOperations(), key);
    }

    @Override
    public void removeRange(long start, long end) {
        proxy.removeRange(zSetOperations(), start, end);
    }

    @Override
    public void removeRangeByScore(double min, double max) {
        proxy.removeRangeByScore(zSetOperations(), min, max);
    }

    @Override
    public double score(String key) {
        return proxy.score(zSetOperations(), key);
    }

    @Override
    public int size() {
        return (int) proxy.size(zSetOperations());
    }

    @Override
    public boolean isEmpty() {
        return 0 >= size();
    }

    @Override
    public void add(Object o) {
        throw new UnsupportedOperationException("Redis ZSet can't add");
    }

    @Override
    public boolean remove(Object o) {
        return 0 < proxy.remove(zSetOperations(), o);
    }

    @Override
    public void clear() {
        proxy.clear(redisTemplate, path());
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Redis List can't get iterator");
    }

    private final BoundZSetOperations<String, String> zSetOperations() {
        return redisTemplate.boundZSetOps(path());
    }

    @Override
    public String path() {
        return CachePathUtil.cachePath2String(super.path(), "zSet");
    }
}