package com.phiau.cache.redis;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/12/15 12:08
 */
public class CacheRedisZSetTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisZSetServiceTest service;

    @Test
    public void test() {
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";
        service.clear();
        service.add(key1, 10);
        service.incrementScore(key1, 10);
        Assert.assertTrue(service.score(key1) == 20);
        Assert.assertTrue(1 == service.count(20, 20));
        Set<String> set = service.range(0, 0);
        Assert.assertTrue(1 == set.size());
        set = service.reverseRange(0, 0);
        Assert.assertTrue(1 == set.size());
        set = service.rangeByScore(20, 20);
        Assert.assertTrue(1 == set.size());
        set = service.reverseRangeByScore(20, 20);
        Assert.assertTrue(1 == set.size());
        Assert.assertTrue(service.rank(key1) == 0);
        Assert.assertTrue(service.reverseRank(key1) == 0);
        Assert.assertTrue(service.remove(key1));

        service.add(key1, 15);
        service.add(key2, 10);
        service.add(key3, 20);
        Assert.assertTrue(1 == service.rank(key1));
        service.removeRange(0, 0);
        Assert.assertTrue(0 == service.rank(key1));
        Assert.assertTrue(1 == service.reverseRank(key1));
        service.removeRangeByScore(20, 20);
        Assert.assertTrue(service.rank(key1) == service.reverseRank(key1));
    }


    @Component
    public static class CacheRedisZSetServiceTest extends AbstractCacheRedisZSet {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "CacheRedisZSetServiceTest");
        }
    }

}
