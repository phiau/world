package com.phiau.cache.redis;

import com.phiau.cache.base.CachePathUtil;
import com.phiau.cache.base.ICacheSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<V> string2Entity(Collection<String> ss) {
        if (null == ss) return null;
        return ss.stream().map(s -> decode(s)).collect(Collectors.toList());
    }

    @Override
    public String encode(V v) {
        return JsonUtil.toJsonString(v);
    }

    @Override
    public V decode(String s) {
        if (null == s) return null;
        return JsonUtil.toInstance(s, clazz);
    }

    public String path() {
        return CachePathUtil.cachePath2String("com", "phiau");
    }
}
