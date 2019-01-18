package com.phiau.cache.business;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.redis.JsonUtil;
import com.phiau.cache.redis.prefix.AbstractCacheRedisZSetPrefix;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2019/1/11 14:42
 */
public class WBHZServiceTest extends BaseJunit4Test {

    @Component
    public static class WBHService extends AbstractCacheRedisZSetPrefix {

        public WBHService() {
            super(1);
        }

        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "WBHService");
        }
    }

    @Autowired
    private WBHService service;

    @Test
    public void test() {
        String prefix = "prefix";
        service.clear(prefix);

        String key = "";
        double score = 90.f;
        for (int i=0; i<10; i++) {
            service.add(prefix, key + i, score + i);
        }
        for (int i=0; i<10; i++) {
            service.incrementScore(prefix, key + i, 2);
        }
        for (int i=0; i<10; i++) {
            System.out.println(String.format("key:%s, rank:%d", key + i, service.rank(prefix, key + i)));
        }
        for (int i=0; i<10; i++) {
            System.out.println(String.format("key:%s, reverse rank:%d", key + i, service.reverseRank(prefix, key + i)));
        }

        service.removeRange(prefix, 3, 5);

        int begin = 2;
        int end = 6;
        Set<String> set = service.range(prefix, begin, end);
        System.out.println(String.format("rank[%d-%d] %s", begin, end, JsonUtil.toJsonString(set)));
        Set<String> reverseSet = service.reverseRange(prefix, begin, end);
        System.out.println(String.format("reverse rank[%d-%d] %s", begin, end, JsonUtil.toJsonString(reverseSet)));
    }
}
