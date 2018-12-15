package com.phiau.cache.redis;

import com.phiau.BaseJunit4Test;
import com.phiau.cache.base.CachePathUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhenbiao.cai
 * @date 2018/12/15 10:46
 */
public class CacheRedisAtomicIntegerTest extends BaseJunit4Test {

    @Autowired
    private CacheRedisAtomicIntegerServiceTest service;

    @Test
    public void test() {
        service.reset();
        int initValue = service.initValue;
        Assert.assertTrue(initValue == service.get());
        initValue += 2;
        Assert.assertTrue(initValue == service.addAndGet(2));
        service.compareAndSet(initValue, initValue + 3);
        initValue += 3;
        Assert.assertTrue(service.get() == initValue);
        initValue--;
        Assert.assertTrue(service.decrementAndGet() == initValue);
        Assert.assertTrue(service.getAndAdd(3) == initValue);
        initValue += 3;
        Assert.assertTrue(service.get() == initValue);
        Assert.assertTrue(service.getAndDecrement() == initValue);
        initValue--;
        Assert.assertTrue(service.get() == initValue);
        Assert.assertTrue(service.getAndIncrement() == initValue);
        initValue++;
        Assert.assertTrue(service.get() == initValue);
        Assert.assertTrue(service.getAndSet(initValue + 5) == initValue);
        initValue += 5;
        Assert.assertTrue(service.get() == initValue);
        initValue++;
        Assert.assertTrue(service.incrementAndGet() == initValue);
    }

    @Component
    public static class CacheRedisAtomicIntegerServiceTest extends AbstractCacheRedisAtomicInteger {

        public int initValue = 2;

        public CacheRedisAtomicIntegerServiceTest() {
        }

        @Override
        public int initValue() {
            return initValue;
        }

        @Override
        public String path() {
            return CachePathUtil.cachePath2String(super.path(), "CacheRedisAtomicIntegerServiceTest");
        }
    }
}
