package com.phiau.cache.business;

import com.phiau.cache.core.ICacheZSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 默认排序，从小到大，可以通过设置 reverse 反转
 * @author zhenbiao.cai
 * @date 2018/12/17 12:20
 */
public class AbstractRankingListCache implements IRankingList<String> {

    /**
     * zSet 默认的是从小到大
     */
    private ICacheZSet zSet;
    /**
     * 第一名从多少开始（0,1……），正常都是从 1 开始
     */
    private int firstValue = 1;
    /**
     * 是否反方向，默认是从大排到小
     */
    private boolean reverse;

    public AbstractRankingListCache(boolean reverse, ICacheZSet zSet) {
        this.reverse = reverse;
        this.zSet = zSet;
    }

    /**
     * 修正传进来的排名参数，rank=2，因为 zset 第一名是从 0 开始，所以修正后的排名是 1
     * @param rank
     * @return
     */
    private final int fixRankParam(int rank) {
        return rank - firstValue;
    }

    /**
     * 修正返回的排名，rank=2，因为 zset 第一名是从 0 开始，所以修正后的排名是 3
     * @param rank
     * @return
     */
    private final long fixRankRet(long rank) {
        return rank + firstValue;
    }

    @Override
    public String first() {
        if (reverse) {
            return zSet.range(0);
        }
        return zSet.reverseRange(0);
    }

    @Override
    public String last() {
        if (reverse) {
            return zSet.reverseRange(0);
        }
        return zSet.range(0);
    }

    @Override
    public long rank(String key) {
        long r;
        if (reverse) {
            r = zSet.rank(key);
        } else {
            r = zSet.reverseRank(key);
        }
        return fixRankRet(r);
    }

    @Override
    public boolean remove(String key) {
        return zSet.remove(key);
    }

    /**
     * 因为 zset 的排名有顺序，从大到小或者从小到大，但是这个删除接口却只有一个方向……所以需要自己处理另一个方向
     * @param rank
     */
    @Override
    public void removeByRank(int rank) {
        rank = fixRankParam(rank);
        if (!reverse) {
            // 这里得算出反方向的排名
            rank = zSet.size() - rank - 1;
        }
        zSet.removeRange(rank, rank);
    }

    @Override
    public double score(String key) {
        return zSet.score(key);
    }

    @Override
    public void setScore(String key, double delta) {
        zSet.add(key, delta);
    }

    @Override
    public double incrementScore(String key, double delta) {
        return zSet.incrementScore(key, delta);
    }

    @Override
    public List<String> between(int rank1, int rank2) {
        rank1 = fixRankParam(rank1);
        rank2 = fixRankParam(rank2);
        Set<String> rs;
        if (reverse) {
            rs = zSet.range(rank1, rank2);
        } else {
            rs = zSet.reverseRange(rank1, rank2);
        }
        if (null != rs && 0 < rs.size()) {
            return new ArrayList<>(rs);
        }
        return null;
    }

    @Override
    public String getByRank(int rank) {
        rank = fixRankParam(rank);
        if (reverse) {
            return zSet.range(rank);
        }
        return zSet.reverseRange(rank);
    }

    @Override
    public void clear() {
        zSet.clear();
    }

    @Override
    public List<String> all() {
        Set<String> rs = zSet.range(0, -1);
        if (null != rs && 0 < rs.size()) {
            return new ArrayList<>(rs);
        }
        return null;
    }

    @Override
    public int size() {
        return zSet.size();
    }
}
