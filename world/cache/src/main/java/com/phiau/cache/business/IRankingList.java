package com.phiau.cache.business;

import java.util.List;

/**
 * 排行榜（第一名是 1）
 * @author zhenbiao.cai
 * @date 2018/12/17 11:43
 */
public interface IRankingList<T> {

    /**
     * 第一名
     * @return
     */
    T first();

    /**
     * 最后一名
     * @return
     */
    T last();

    /**
     * 获得 key 的名次
     * @param key
     * @return
     */
    long rank(T key);

    /**
     * 从榜单移除
     * @param key
     * @return
     */
    boolean remove(T key);

    /**
     * 移除第几名
     * @param rank
     * @return
     */
    void removeByRank(int rank);

    /**
     * 获得分数，如果不在榜上，分数为 0
     * @param key
     * @return
     */
    double score(T key);

    /**
     * 设置 key 的得分，如果 key 不在榜上，则新加入，否则覆盖原来的分数
     * @param key
     * @param delta
     * @return
     */
    void setScore(T key, double delta);

    /**
     * 增加 key 的得分
     * @param key
     * @param delta
     * @return
     */
    double incrementScore(T key, double delta);

    /**
     * 获得名次介于 rank1 到 rank2 之间的对象列表，包括 rank1、rank2
     * @param rank1
     * @param rank2
     * @return
     */
    List<T> between(int rank1, int rank2);

    /**
     * 通过名次获得对象
     * @param rank
     * @return
     */
    T getByRank(int rank);

    /**
     * 获得排行榜所有数据
     * @return
     */
    List<T> all();

    /**
     * 清空排行榜
     */
    void clear();

    /**
     * 排行榜数量
     * @return
     */
    int size();
}
