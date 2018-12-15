package com.phiau.cache.redis.proxy;

import com.phiau.cache.base.ICacheSerialize;
import org.springframework.data.redis.core.BoundZSetOperations;

import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/12/15 11:49
 */
public class CacheRedisZSetProxy extends AbstractRedisProxy {

    public CacheRedisZSetProxy(ICacheSerialize serialize) {
        super(serialize);
    }

    public void add(BoundZSetOperations operations, String key, double score) {
        operations.add(key, score);
    }

    public long count(BoundZSetOperations operations, double min, double max) {
        return operations.count(min, max);
    }

    public double incrementScore(BoundZSetOperations operations, String key, double delta) {
        return operations.incrementScore(key, delta);
    }

    public Set<String> range(BoundZSetOperations operations, long start, long end) {
        return operations.range(start, end);
    }

    public Set<String> reverseRange(BoundZSetOperations operations, long start, long end) {
        return operations.reverseRange(start, end);
    }

    public Set<String> rangeByScore(BoundZSetOperations operations, double min, double max) {
        return operations.rangeByScore(min, max);
    }

    public Set<String> reverseRangeByScore(BoundZSetOperations operations, double min, double max) {
        return operations.reverseRangeByScore(min, max);
    }

    public long rank(BoundZSetOperations operations, String key) {
        return operations.rank(key);
    }

    public long reverseRank(BoundZSetOperations operations, String key) {
        return operations.reverseRank(key);
    }

    public long remove(BoundZSetOperations operations, Object... vs) {
        return operations.remove(vs);
    }

    public void removeRange(BoundZSetOperations operations, long start, long end) {
        operations.removeRange(start, end);
    }

    public void removeRangeByScore(BoundZSetOperations operations, double min, double max) {
        operations.removeRangeByScore(min, max);
    }

    public double score(BoundZSetOperations operations, String key) {
        return operations.score(key);
    }

    public long size(BoundZSetOperations operations) {
        return operations.size();
    }
}
