package com.phiau.cache.business;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.redis.AbstractCacheRedisZSet;
import com.phiau.cache.redis.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2019/1/11 16:00
 */
public class SpaPlayerZSetServiceTest extends BaseJunit4Test {

    @Component
    public static class SPZService extends AbstractCacheRedisZSet {
        public SPZService() {
            super(0);
        }

        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "SPZService");
        }
    }

    @Autowired
    private SPZService service;

    @Test
    public void test() {
        service.clear();

        String key = "";
        double score = 90.f;
        for (int i=0; i<10; i++) {
            service.add(key + i, score + i);
        }
        for (int i=0; i<10; i++) {
            service.incrementScore(key + i, 2);
        }
        for (int i=0; i<10; i++) {
            System.out.println(String.format("key:%s, rank:%d", key + i, service.rank(key + i)));
        }
        for (int i=0; i<10; i++) {
            System.out.println(String.format("key:%s, reverse rank:%d", key + i, service.reverseRank(key + i)));
        }

        service.removeRange(3, 5);

        int begin = 2;
        int end = 6;
        Set<String> set = service.range(begin, end);
        System.out.println(String.format("rank[%d-%d] %s", begin, end, JsonUtil.toJsonString(set)));
        Set<String> reverseSet = service.reverseRange(begin, end);
        System.out.println(String.format("reverse rank[%d-%d] %s", begin, end, JsonUtil.toJsonString(reverseSet)));
    }
}
