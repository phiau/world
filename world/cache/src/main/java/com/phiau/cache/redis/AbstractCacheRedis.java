package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.base.ICacheSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 22:42
 */
public abstract class AbstractCacheRedis<V> extends AbstractCacheRedisLock implements ICacheSerialize<V> {

    protected Class<V> clazz;

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @SuppressWarnings("unchecked")
    public AbstractCacheRedis() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            this.clazz = (Class<V>) actualTypeArguments[0];
        } else {
            this.clazz = (Class<V>) genericSuperclass;
        }
    }

    @Override
    public String encode(V v) {
        return JsonUtil.toJsonString(v);
    }

    @Override
    public V decode(String s) {
        return JsonUtil.toInstance(s, clazz);
    }

    public String path() {
        return CachePathUtil.cachePath2String("com", "phiau");
    }
}
