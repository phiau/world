package com.phiau.cache.core.prefix;

import java.util.Set;

/**
 * 接口和 ICacheZSet 保护一致
 * @author zhenbiao.cai
 * @date 2018/12/15 15:08
 */
public interface ICacheZSetPrefix extends ICacheCollectionPrefix {

    /**
     * 增加元素
     * @param key 键，如玩家 id
     * @param score 分数
     */
    void add(String prefixKey, String key, double score);

    /**
     * 分数在 min 和 max 之间的元素个数
     * @param min
     * @param max
     * @return
     */
    long count(String prefixKey, double min, double max);

    /**
     * 为排行榜中的 v 元素增加分数
     * @param key
     * @param delta
     * @return
     */
    double incrementScore(String prefixKey, String key, double delta);

    /**
     * 得到排名在 start 和 end 之间的元素有序集合
     * @param start 从 0 开始算
     * @param end 如果是 -1，标识最后一个
     * @return
     */
    Set<String> range(String prefixKey, long start, long end);

    Set<String> reverseRange(String prefixKey, long start, long end);

    /**
     * 得到分数在 min 和 max 之间的元素有序集合
     * @param min
     * @param max
     * @return
     */
    Set<String> rangeByScore(String prefixKey, double min, double max);

    Set<String> reverseRangeByScore(String prefixKey, double min, double max);

    /**
     *得到元素排名，第一名为 0
     * @param key
     * @return
     */
    long rank(String prefixKey, String key);

    long reverseRank(String prefixKey, String key);

    void removeRange(String prefixKey, long start, long end);

    void removeRangeByScore(String prefixKey, double min, double max);

    /**
     * 得到元素的得分
     * @param key
     * @return
     */
    double score(String prefixKey, String key);
}
