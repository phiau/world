package com.phiau.cache.redis;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhenbiao.cai
 * @date 2018/11/27 17:08
 */
public class CacheRedisSetTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisSetServiceTest service;

    @Test
    public void test() {
        CacheRedisSetEntity entity00 = new CacheRedisSetEntity("entity00");
        CacheRedisSetEntity entity01 = new CacheRedisSetEntity("entity01");
        CacheRedisSetEntity entity02 = new CacheRedisSetEntity("entity02");
        /** 先清空 */
        service.clear();
        Assert.assertTrue(!service.contains(entity00));
        service.add(entity00);
        Assert.assertTrue(service.contains(entity00));
        service.remove(entity00);
        Assert.assertTrue(service.isEmpty());
        Assert.assertTrue(0 >= service.size());
        /** random */
        service.add(entity00);
        Assert.assertTrue(service.randomMember().equals(entity00));
        for (CacheRedisSetEntity e : service.randomMembers(2)) {
            Assert.assertTrue(entity00.equals(e));
        }
        /** add */
        service.add(entity01);
        service.add(entity01);
        service.add(entity02);
        Assert.assertTrue(3 == service.size());
        /** remove */
        service.remove(entity01);
        service.remove(entity02);
        Assert.assertTrue(1 == service.size());
        for (CacheRedisSetEntity e : service.randomMembers(2)) {
            Assert.assertTrue(entity00.equals(e));
        }
        /** clear */
        service.clear();
        Assert.assertTrue(service.isEmpty());
    }


    @Component
    public static class CacheRedisSetServiceTest extends AbstractCacheRedisSet<CacheRedisSetEntity> {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "CacheRedisSetServiceTest");
        }
    }

    private static class CacheRedisSetEntity {
        private String content;

        public CacheRedisSetEntity() {}
        public CacheRedisSetEntity(String content) {this.content = content; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj) return false;
            if (getClass() != obj.getClass()) return false;
            CacheRedisSetTest.CacheRedisSetEntity other = (CacheRedisSetTest.CacheRedisSetEntity) obj;
            return content.equals(other.content);
        }
    }
}
