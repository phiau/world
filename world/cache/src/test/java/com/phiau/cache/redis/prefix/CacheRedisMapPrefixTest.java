package com.phiau.cache.redis.prefix;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/28 20:30
 */
public class CacheRedisMapPrefixTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisMapPrefixServiceTest service;

    @Test
    public void test() {
        String prefix01 = "prefix01";
        String primary01 = "primary01";
        String content01 = "content01";
        /** 先清空 */
        service.clear(prefix01);
        CacheRedisMapPrefixEntity entity01 = new CacheRedisMapPrefixEntity(prefix01, primary01, content01);
        /** 添加 */
        service.put(entity01.prefix, entity01.primary, entity01);
        boolean contains = service.containsKey(prefix01, primary01);
        Assert.assertTrue(contains);
        /** 删除 */
        service.remove(prefix01, primary01);
        contains = service.containsKey(prefix01, primary01);
        Assert.assertTrue(!contains);
        boolean empty = service.isEmpty(prefix01);
        Assert.assertTrue(empty);
        /** 获取 */
        service.put(entity01.getPrefix(), entity01.primary, entity01);
        CacheRedisMapPrefixTest.CacheRedisMapPrefixEntity otherEntity = service.get(prefix01, primary01);
        Assert.assertTrue(entity01.equals(otherEntity));
        empty = service.isEmpty(prefix01);
        Assert.assertTrue(!empty);
        /** 键集合 */
        Set<String> keySet = service.keySet(prefix01);
        Assert.assertTrue(1 == keySet.size() && keySet.contains(primary01));
        /** 值集合 */
        Collection<CacheRedisMapPrefixTest.CacheRedisMapPrefixEntity> value = service.values(prefix01);
        Assert.assertTrue(1 == value.size() && value.contains(entity01));
        /** 清空 */
        service.clear(prefix01);
        Assert.assertTrue(service.isEmpty(prefix01));
        Assert.assertTrue(0 >= service.size(prefix01));
    }


    @Component
    public static class CacheRedisMapPrefixServiceTest extends AbstractCacheRedisMapPrefix<CacheRedisMapPrefixEntity> {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "cacheRedisMapPrefixServiceTest");
        }
    }

    private static class CacheRedisMapPrefixEntity {
        private String prefix;
        private String primary;
        private String content;

        public CacheRedisMapPrefixEntity() {}

        public CacheRedisMapPrefixEntity(String prefix, String primary, String content) {
            this.prefix = prefix;
            this.primary = primary;
            this.content = content;
        }
        public String getPrefix() { return prefix; }
        public void setPrefix(String prefix) { this.prefix = prefix; }
        public String getPrimary() { return primary; }
        public void setPrimary(String primary) { this.primary = primary; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj) return false;
            if (getClass() != obj.getClass()) return false;
            CacheRedisMapPrefixTest.CacheRedisMapPrefixEntity other = (CacheRedisMapPrefixTest.CacheRedisMapPrefixEntity) obj;
            return prefix.equals(other.prefix)&&primary.equals(other.primary) && content.equals(other.content);
        }
    }
}
