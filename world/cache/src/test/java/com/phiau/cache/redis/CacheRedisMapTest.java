package com.phiau.cache.redis;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.base.ICacheMapPrimaryKey;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhenbiao.cai
 * @date 2018/11/27 14:22
 */
public class CacheRedisMapTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisMapServiceTest service;

    @Test
    public void test() {
        /** 先清空 */
        service.clear();
        int primary01 = 1;
        CacheRedisMapEntity redisMapEntity = new CacheRedisMapEntity();
        redisMapEntity.setPrimary(primary01);
        redisMapEntity.setContent("content01");
        /** 添加 */
        service.put(redisMapEntity);
        boolean contains = service.containsKey(primary01);
        Assert.assertTrue(contains);
        /** 删除 */
        service.remove(primary01);
        contains = service.containsKey(primary01);
        Assert.assertTrue(!contains);
        boolean empty = service.isEmpty();
        Assert.assertTrue(empty);
        /** 获取 */
        service.put(redisMapEntity);
        CacheRedisMapEntity otherEntity = service.get(primary01);
        Assert.assertTrue(redisMapEntity.equals(otherEntity));
        empty = service.isEmpty();
        Assert.assertTrue(!empty);
        /** 键集合 */
        Set<String> keySet = service.keySet();
        Assert.assertTrue(1 == keySet.size() && keySet.contains(String.valueOf(primary01)));
        /** 值集合 */
        Collection<CacheRedisMapEntity> value = service.values();
        Assert.assertTrue(1 == value.size() && value.contains(redisMapEntity));
        /** 清空 */
        service.clear();
        Assert.assertTrue(service.isEmpty());
        Assert.assertTrue(0 >= service.size());
    }

    @Component
    public static class CacheRedisMapServiceTest extends AbstractCacheRedisMap<CacheRedisMapEntity> {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "cacheRedisMapServiceTest");
        }
    }

    private static class CacheRedisMapEntity implements ICacheMapPrimaryKey {
        private int primary;
        private String content;

        public int getPrimary() { return primary; }
        public void setPrimary(int primary) { this.primary = primary; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj) return false;
            if (getClass() != obj.getClass()) return false;
            CacheRedisMapEntity other = (CacheRedisMapEntity) obj;
            return primary == other.primary && content.equals(other.content);
        }

        @Override
        public Object primaryKey() {
            return primary;
        }
    }
}
