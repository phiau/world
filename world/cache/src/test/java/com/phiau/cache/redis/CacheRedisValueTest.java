package com.phiau.cache.redis;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhenbiao.cai
 * @date 2019/1/2 16:50
 */
public class CacheRedisValueTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisValueServiceTest service;

    @Test
    public void test() {
        String name = "e1", sourceAge = "10", updateName = "15";
        CacheRedisValueEntity entity = new CacheRedisValueEntity(name, sourceAge);
        service.delete(entity.name);
        service.set(entity.getName(), entity);
        CacheRedisValueEntity other = service.get(entity.getName());
        Assert.assertTrue(entity.equals(other));

        entity.age = updateName;
        CacheRedisValueEntity other1 = service.getAndSet(entity.getName(), entity);
        Assert.assertTrue(other1.age.equals(sourceAge));
        other1 = service.getAndSet(entity.getName(), entity);
        Assert.assertTrue(other1.age.equals(updateName));

    }

    @Component
    public static class CacheRedisValueServiceTest extends AbstractCacheRedisValue<CacheRedisValueEntity> {
        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "cacheRedisValueServiceTest");
        }
    }

    private static class CacheRedisValueEntity {
        private String name, age;

        public CacheRedisValueEntity() {}
        public CacheRedisValueEntity(String name, String age) { this.name = name; this.age = age; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getAge() { return age; }
        public void setAge(String age) { this.age = age; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj) return false;
            if (getClass() != obj.getClass()) return false;
            CacheRedisValueEntity other = (CacheRedisValueEntity) obj;
            return name.equals(other.name);
        }
    }
}
