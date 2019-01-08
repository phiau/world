package com.phiau.cache.redis;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhenbiao.cai
 * @date 2018/11/27 14:57
 */
public class CacheRedisListTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisListServiceTest service;

    @Test
    public void test() {
        CacheRedisListEntity entity00 = new CacheRedisListEntity("entity00");
        CacheRedisListEntity entity01 = new CacheRedisListEntity("entity01");
        CacheRedisListEntity entity02 = new CacheRedisListEntity("entity02");
        CacheRedisListEntity entity03 = new CacheRedisListEntity("entity03");
        /** 先清空 */
        service.clear();
        Assert.assertTrue(service.isEmpty());
        Assert.assertTrue(0 == service.size());
        /** 加入一个 */
        service.add(entity00);
        Assert.assertTrue(1 == service.size());
        /** 再加入一个 */
        service.add(entity01);
        Assert.assertTrue(2 == service.size());

        CacheRedisListEntity entityVerify00 = service.get(0);
        CacheRedisListEntity entityVerify01 = service.get(1);
        Assert.assertTrue(entityVerify00.equals(entity00));
        Assert.assertTrue(entityVerify01.equals(entity01));
        /** set */
        service.set(1, entity00);
        entityVerify01 = service.get(1);
        Assert.assertTrue(!entity01.equals(entityVerify01));
        /** first last */
        CacheRedisListEntity first = service.getAndRemoveFirst();
        CacheRedisListEntity last = service.getAndRemoveLast();
        Assert.assertTrue(first.equals(entity00));
        Assert.assertTrue(!last.equals(entity01));

        /** 重新加入 */
        service.add(entity00);
        service.add(entity01);
        service.add(entity02);
        service.add(entity03);
        /** sub */
        List<CacheRedisListEntity> list = service.subList(1, 2);
        Assert.assertTrue(entity01.equals(list.get(0)));
        Assert.assertTrue(entity02.equals(list.get(1)));
        /** remove */
        CacheRedisListEntity rmEntity = service.removeByIndex(2);
        Assert.assertTrue(rmEntity.equals(entity02));
        Assert.assertTrue(!service.remove(rmEntity));
        /** 重新加入，然后在删除 */
        service.add(rmEntity);
        Assert.assertTrue(service.remove(rmEntity));
        /** 清空 */
        service.clear();
        Assert.assertTrue(service.isEmpty());
        Assert.assertTrue(0 >= service.size());
    }

    @Component
    public static class CacheRedisListServiceTest extends AbstractCacheRedisList<CacheRedisListEntity> {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "CacheRedisListServiceTest");
        }
    }

    private static class CacheRedisListEntity {
        private String content;

        public CacheRedisListEntity() {}
        public CacheRedisListEntity(String content) { this.content = content; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj) return false;
            if (getClass() != obj.getClass()) return false;
            CacheRedisListEntity other = (CacheRedisListEntity) obj;
            return content.equals(other.content);
        }
    }
}
