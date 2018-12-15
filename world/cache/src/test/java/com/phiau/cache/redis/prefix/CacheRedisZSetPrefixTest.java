package com.phiau.cache.redis.prefix;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/12/15 15:17
 */
public class CacheRedisZSetPrefixTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisZSetPrefixServiceTest service;

    @Test
    public void test() {
        String prefix = "prefix";
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";
        service.clear(prefix);
        service.add(prefix, key1, 10);
        service.incrementScore(prefix, key1, 10);
        Assert.assertTrue(service.score(prefix, key1) == 20);
        Assert.assertTrue(1 == service.count(prefix, 20, 20));
        Set<String> set = service.range(prefix, 0, 0);
        Assert.assertTrue(1 == set.size());
        set = service.reverseRange(prefix, 0, 0);
        Assert.assertTrue(1 == set.size());
        set = service.rangeByScore(prefix, 20, 20);
        Assert.assertTrue(1 == set.size());
        set = service.reverseRangeByScore(prefix, 20, 20);
        Assert.assertTrue(1 == set.size());
        Assert.assertTrue(service.rank(prefix, key1) == 0);
        Assert.assertTrue(service.reverseRank(prefix, key1) == 0);
        Assert.assertTrue(service.remove(prefix, key1));

        service.add(prefix, key1, 15);
        service.add(prefix, key2, 10);
        service.add(prefix, key3, 20);
        Assert.assertTrue(1 == service.rank(prefix, key1));
        service.removeRange(prefix, 0, 0);
        Assert.assertTrue(0 == service.rank(prefix, key1));
        Assert.assertTrue(1 == service.reverseRank(prefix, key1));
        service.removeRangeByScore(prefix, 20, 20);
        Assert.assertTrue(service.rank(prefix, key1) == service.reverseRank(prefix, key1));
    }

    @Component
    public static class CacheRedisZSetPrefixServiceTest extends AbstractCacheRedisZSetPrefix {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "CacheRedisZSetPrefixServiceTest");
        }
    }
}
