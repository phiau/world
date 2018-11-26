package com.phiau.cache.base;

import java.util.concurrent.TimeUnit;

/**
 * User: zhenbiao.cai
 * Date: 2018-11-26 21:52
 */
public interface ICacheLock {

    void lock(int childKey);

    void lock(String childKey);

    void lock(int childKey, long time, TimeUnit unit);

    void lock(String childKey, long time, TimeUnit unit);

    void unlock(int childKey);

    void unlock(String childKey);

    boolean tryLock(int key);

    boolean tryLock(String key);

    boolean tryLock(String key, int time);

    boolean tryLock(int key, int time, TimeUnit unit);

    boolean tryLock(String key, int time, TimeUnit unit);
}
