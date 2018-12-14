package com.phiau.cache.redis.prefix;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhenbiao.cai
 * @date 2018/12/14 12:00
 */
public class CacheRedisSetPrefixTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisSetPrefixTest.CacheRedisSetPrefixServiceTest service;

    @Test
    public void test() {
        String prefix = "prefix";
        CacheRedisSetPrefixEntity entity00 = new CacheRedisSetPrefixEntity(prefix, "entity00");
        CacheRedisSetPrefixEntity entity01 = new CacheRedisSetPrefixEntity(prefix, "entity01");
        CacheRedisSetPrefixEntity entity02 = new CacheRedisSetPrefixEntity(prefix, "entity02");
        /** 先清空 */
        service.clear(prefix);
        Assert.assertTrue(!service.contains(prefix, entity00));
        service.add(prefix, entity00);
        Assert.assertTrue(service.contains(prefix, entity00));
        service.remove(prefix, entity00);
        Assert.assertTrue(service.isEmpty(prefix));
        Assert.assertTrue(0 >= service.size(prefix));
        /** random */
        service.add(prefix, entity00);
        Assert.assertTrue(service.randomMember(prefix).equals(entity00));
        for (CacheRedisSetPrefixEntity e : service.randomMembers(prefix, 2)) {
            Assert.assertTrue(entity00.equals(e));
        }
        /** add */
        service.add(prefix, entity01);
        service.add(prefix, entity01);
        service.add(prefix, entity02);
        Assert.assertTrue(3 == service.size(prefix));
        /** remove */
        service.remove(prefix, entity01);
        service.remove(prefix, entity02);
        Assert.assertTrue(1 == service.size(prefix));
        for (CacheRedisSetPrefixEntity e : service.randomMembers(prefix, 2)) {
            Assert.assertTrue(entity00.equals(e));
        }
        /** clear */
        service.clear(prefix);
        Assert.assertTrue(service.isEmpty(prefix));
    }

    @Component
    public static class CacheRedisSetPrefixServiceTest extends AbstractCacheRedisSetPrefix<CacheRedisSetPrefixEntity> {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "cacheRedisSetPrefixServiceTest");
        }
    }

    private static class CacheRedisSetPrefixEntity {
        private String prefix;
        private String content;

        public CacheRedisSetPrefixEntity() {}
        public CacheRedisSetPrefixEntity(String prefix, String content) {
            this.prefix = prefix;
            this.content = content;
        }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getPrefix() { return prefix; }
        public void setPrefix(String prefix) { this.prefix = prefix; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj) return false;
            if (getClass() != obj.getClass()) return false;
            CacheRedisSetPrefixEntity other = (CacheRedisSetPrefixEntity) obj;
            return content.equals(other.content) && prefix.equalsIgnoreCase(other.prefix);
        }
    }
}
