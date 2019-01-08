package com.phiau.cache.business;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.core.ICacheZSet;
import com.phiau.cache.redis.AbstractCacheRedisZSet;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RankingListCacheTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisZSetServiceTest zSetService;
    @Autowired
    private CacheRedisZSetServiceReverseTest zSetReverseService;

    private List<String> list;

    private void init(int size) {
        for (int i=0; i<size; i++) {
            list.add(String.valueOf(i + 1));
        }
    }

    private void testComm(IRankingList service, String key, boolean reverse) {
        Assert.assertTrue(service.first().equals(key + 9));
        Assert.assertTrue(service.last().equals(key + 1));
        for (int i=1; i<10; i++) {
            String tmp = key + i;
            if (reverse) {
                Assert.assertTrue(service.score(tmp) == 10 - i);
            } else {
                Assert.assertTrue(service.score(tmp) == i);
            }
            Assert.assertTrue(service.rank(tmp) == 10 - i);

        }
        service.removeByRank(3);
        List<String> list = service.between(2, 4);
        Set<String> set = new HashSet<>(list);
        Assert.assertTrue(set.contains(key + 8) && set.contains(key + 6) && set.contains(key + 5));
        service.remove(key + 6);
        list = service.between(2, 4);
        set = new HashSet<>(list);
        Assert.assertTrue(set.contains(key + 8) && set.contains(key + 5) && set.contains(key + 4));
        Assert.assertTrue(service.getByRank(3).equals(key + 5));
        Assert.assertTrue(7 == service.size());
    }

    @Test
    public void testReverse() {
        boolean reverse = false;
        RankingListCacheServiceTest service = new RankingListCacheServiceTest(reverse, zSetReverseService);
        service.clear();
        String key = "keyReverse";
        for (int i=1; i<10; i++) {
            service.setScore(key + i, i);
        }
        testComm(service, key, reverse);
    }

    @Test
    public void test() {
        boolean reverse = true;
        RankingListCacheServiceTest service = new RankingListCacheServiceTest(reverse, zSetService);
        service.clear();
        String key = "key";
        for (int i=1; i<10; i++) {
            service.setScore(key + i, 10 - i);
        }
        testComm(service, key, reverse);
    }

    @Test
    public void testFixedSize() {
        boolean reverse = true;
        RankingListCacheServiceTest service = new RankingListCacheServiceTest(reverse, 10, zSetService);
        service.clear();
        String key = "key";
        int size = 16;
        for (int i=1; i<size; i++) {
            service.setScore(key + i, size - i);
        }
    }

    public class RankingListCacheServiceTest extends AbstractRankingListCache {
        public RankingListCacheServiceTest(boolean reverse, ICacheZSet zSet) {
            super(reverse, zSet);
        }
        public RankingListCacheServiceTest(boolean reverse, int fixedSize, ICacheZSet zSet) {
            super(reverse, fixedSize, zSet);
        }
    }

    @Component
    public static class CacheRedisZSetServiceReverseTest extends AbstractCacheRedisZSet {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "CacheRedisZSetServiceReverseTest");
        }
    }

    @Component
    public static class CacheRedisZSetServiceTest extends AbstractCacheRedisZSet {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "CacheRedisZSetServiceTest");
        }
    }
}
