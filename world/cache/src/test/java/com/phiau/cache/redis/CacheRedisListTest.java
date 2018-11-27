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
        CacheRedisListEntry entry00 = new CacheRedisListEntry();
        entry00.setContent("entry00");
        CacheRedisListEntry entry01 = new CacheRedisListEntry();
        entry01.setContent("entry01");
        CacheRedisListEntry entry02 = new CacheRedisListEntry();
        entry02.setContent("entry02");
        CacheRedisListEntry entry03 = new CacheRedisListEntry();
        entry03.setContent("entry03");
        /** 先清空 */
        service.clear();
        Assert.assertTrue(service.isEmpty());
        Assert.assertTrue(0 == service.size());
        /** 加入一个 */
        service.add(entry00);
        Assert.assertTrue(1 == service.size());
        /** 再加入一个 */
        service.add(entry01);
        Assert.assertTrue(2 == service.size());

        CacheRedisListEntry entryVerify00 = service.get(0);
        CacheRedisListEntry entryVerify01 = service.get(1);
        Assert.assertTrue(entryVerify00.equals(entry00));
        Assert.assertTrue(entryVerify01.equals(entry01));
        /** set */
        service.set(1, entry00);
        entryVerify01 = service.get(1);
        Assert.assertTrue(!entry01.equals(entryVerify01));
        /** first last */
        CacheRedisListEntry first = service.getAndRemoveFirst();
        CacheRedisListEntry last = service.getAndRemoveLast();
        Assert.assertTrue(first.equals(entry00));
        Assert.assertTrue(!last.equals(entry01));

        /** 重新加入 */
        service.add(entry00);
        service.add(entry01);
        service.add(entry02);
        service.add(entry03);
        /** sub */
        List<CacheRedisListEntry> list = service.subList(1, 2);
        Assert.assertTrue(entry01.equals(list.get(0)));
        Assert.assertTrue(entry02.equals(list.get(1)));
        /** remove */
        CacheRedisListEntry rmEntry = service.remove(2);
        Assert.assertTrue(rmEntry.equals(entry02));
        Assert.assertTrue(!service.remove(rmEntry));
        /** 重新加入，然后在删除 */
        service.add(rmEntry);
        Assert.assertTrue(service.remove(rmEntry));
        /** 清空 */
        service.clear();
        Assert.assertTrue(service.isEmpty());
        Assert.assertTrue(0 >= service.size());
    }

    @Component
    public static class CacheRedisListServiceTest extends AbstractCacheRedisList<CacheRedisListEntry> {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "CacheRedisListServiceTest");
        }
    }

    private static class CacheRedisListEntry {
        private String content;
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj) return false;
            if (getClass() != obj.getClass()) return false;
            CacheRedisListTest.CacheRedisListEntry other = (CacheRedisListTest.CacheRedisListEntry) obj;
            return content.equals(other.content);
        }
    }
}
