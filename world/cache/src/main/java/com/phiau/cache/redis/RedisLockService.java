package com.phiau.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Component
public class RedisLockService {

	protected static final Logger logger = LoggerFactory.getLogger(RedisLockService.class);
	// redis解锁脚本
	private static final String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\r\n"
			+ "    return redis.call(\"del\",KEYS[1])\r\n" + "else\r\n" + "    return 0\r\n" + "end";

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * redi缓存有效时间，单位：豪秒
	 */
	private static final int CACHE_TIME = 300;
	/**
	 * 重试等待时间 单位毫秒
	 */
	private int RETRY_AWAIT = 300;

	private static final String KEY_PREFIX = "redisLock:";
	private static final String OK = "OK";

	private ThreadLocal<Stack<LockItem>> localLockStack = new ThreadLocal<>();

	private final String prefixKey(String key) {
		return KEY_PREFIX + key;
	}


	/**
	 * 尝试锁住一个 key，成功返回真
	 * @param key
	 * @return
	 */
	public boolean tryLock(String key) {
		return tryLock(key, 3 * CACHE_TIME);
	}

	/**
	 * 尝试锁住一个 key，成功返回真
	 * @param key
	 * @param lockTime 锁住的时间，过了这个时间，会自动删除锁标记
	 * @return
	 */
	public boolean tryLock(String key, int lockTime) {
		key = prefixKey(key);
		Jedis jedis = null;
		try {
			jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
			String uuid = UUID.randomUUID().toString();
			String ret = jedis.set(key, uuid, "NX", "PX", lockTime);
			if (OK.equals(ret)) {
				if (null == localLockStack.get()) {
					localLockStack.set(new Stack<LockItem>());
				}
				localLockStack.get().push(new LockItem(key, uuid));
				return true;
			}
		} catch (Exception e) {
			logger.error("{}", e);
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}
		return false;
	}

	public boolean lock(String key, long time, TimeUnit unit) {
		final long startMillis = System.currentTimeMillis();
		final Long millisToWait = (unit != null) ? unit.toMillis(time) : null;
		key = prefixKey(key);
		Jedis jedis = null;
		try {
			jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
			String uuid = UUID.randomUUID().toString();
			String ret;
			do {
				ret = jedis.set(key, uuid, "NX", "PX", CACHE_TIME);
				if (OK.equals(ret)) {
					break;
				}
				LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(RETRY_AWAIT));
			} while (System.currentTimeMillis() - startMillis - RETRY_AWAIT < millisToWait);
			if (OK.equals(ret)) {
				if (null == localLockStack.get()) {
					localLockStack.set(new Stack<LockItem>());
				}
				localLockStack.get().push(new LockItem(key, uuid));
				return true;
			} else {
				logger.error(String.format(" RedisDistributedLock.lock can not get the lock, wait time:%d, key:%s",
						millisToWait, key));
			}
		} catch (Exception e) {
			logger.error(" RedisDistributedLock.lock error {}  ", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}

	/**
	 * 加锁
	 * 
	 * @param key
	 *            定义的key必须唯一, 建议以类名命名
	 * @warning 有效期时间设置为单线程处理时间的3倍
	 */
	public boolean lock(String key) {
		return lock(key, 3 * RETRY_AWAIT, TimeUnit.MILLISECONDS);
	}


	/**
	 * 解锁特定锁
	 */
	public void unlock(String key) {
		key = prefixKey(key);
		Iterator<LockItem> it = localLockStack.get().iterator();
		LockItem item;
		while (it.hasNext()) {
			item = it.next();
			if (item.k.equals(key)) {
				Jedis jedis = null;
				try {
					jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
					List<String> keys = new ArrayList<>(1);
					keys.add(item.k);
					List<String> args = new ArrayList<>(1);
					args.add(item.v);
					jedis.eval(script, keys, args);
					it.remove();
				} catch (Exception e) {
					logger.error(" RedisDistributedLock.unlock error {}  ", e);
				} finally {
					if (jedis != null) {
						jedis.close();
					}
				}
			}
		}
	}

	private static class LockItem {
		public String k, v;

		public LockItem(String k, String v) {
			this.k = k;
			this.v = v;
		}
	}
}
