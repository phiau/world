package com.phiau.cache.redis.prefix;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhenbiao.cai
 * @date 2018/11/29 20:49
 */
public class CacheRedisListPrefixTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisListPrefixServiceTest service;

    @Test
    public void test() {
        String prefix = "prefix";
        CacheRedisListPrefixEntity entity00 = new CacheRedisListPrefixEntity(prefix, "entity00");
        CacheRedisListPrefixEntity entity01 = new CacheRedisListPrefixEntity(prefix, "entity01");
        CacheRedisListPrefixEntity entity02 = new CacheRedisListPrefixEntity(prefix, "entity02");
        CacheRedisListPrefixEntity entity03 = new CacheRedisListPrefixEntity(prefix, "entity03");
        /** 先清空 */
        service.clear(prefix);
        Assert.assertTrue(service.isEmpty(prefix));
        Assert.assertTrue(0 == service.size(prefix));
        /** 加入一个 */
        service.add(prefix, entity00);
        Assert.assertTrue(1 == service.size(prefix));
        /** 再加入一个 */
        service.add(prefix, entity01);
        Assert.assertTrue(2 == service.size(prefix));

        CacheRedisListPrefixEntity entityVerify00 = service.get(prefix, 0);
        CacheRedisListPrefixEntity entityVerify01 = service.get(prefix, 1);
        Assert.assertTrue(entityVerify00.equals(entity00));
        Assert.assertTrue(entityVerify01.equals(entity01));
        /** set */
        service.set(prefix, 1, entity00);
        entityVerify01 = service.get(prefix, 1);
        Assert.assertTrue(!entity01.equals(entityVerify01));
        /** first last */
        CacheRedisListPrefixEntity first = service.getAndRemoveFirst(prefix);
        CacheRedisListPrefixEntity last = service.getAndRemoveLast(prefix);
        Assert.assertTrue(first.equals(entity00));
        Assert.assertTrue(!last.equals(entity01));

        /** 重新加入 */
        service.add(prefix, entity00);
        service.add(prefix, entity01);
        service.add(prefix, entity02);
        service.add(prefix, entity03);
        /** sub */
        List<CacheRedisListPrefixEntity> list = service.subList(prefix, 1, 2);
        Assert.assertTrue(entity01.equals(list.get(0)));
        Assert.assertTrue(entity02.equals(list.get(1)));
        /** remove */
        CacheRedisListPrefixEntity rmEntity = service.remove(prefix, 2);
        Assert.assertTrue(rmEntity.equals(entity02));
        Assert.assertTrue(!service.remove(prefix, rmEntity));
        /** 重新加入，然后在删除 */
        service.add(prefix, rmEntity);
        Assert.assertTrue(service.remove(prefix, rmEntity));
        /** 清空 */
        service.clear(prefix);
        Assert.assertTrue(service.isEmpty(prefix));
        Assert.assertTrue(0 >= service.size(prefix));
    }


    @Component
    public static class CacheRedisListPrefixServiceTest extends AbstractCacheRedisListPrefix<CacheRedisListPrefixEntity> {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "cacheRedisListPrefixServiceTest");
        }
    }

    private static class CacheRedisListPrefixEntity {
        private String prefix;
        private String content;

        public CacheRedisListPrefixEntity() {}

        public CacheRedisListPrefixEntity(String prefix, String content) {
            this.prefix = prefix;
            this.content = content;
        }

        public String getPrefix() { return prefix; }
        public void setPrefix(String prefix) { this.prefix = prefix; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj) return false;
            if (getClass() != obj.getClass()) return false;
            CacheRedisListPrefixEntity other = (CacheRedisListPrefixEntity) obj;
            return content.equals(other.content) && prefix.equals(other.prefix);
        }
    }
}
