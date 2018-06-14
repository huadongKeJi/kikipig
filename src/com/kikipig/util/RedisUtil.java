package com.kikipig.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

public final class RedisUtil {
	/**日志记录.*/
	private static final Logger LOG = LogManager.getLogger(RedisUtil.class);
    private static RedisTemplate<Serializable, Object> redisTemplate;
    /**默认的缓存失效时间.*/
    private static long defaultCacheExpireTime = 0L;
    /**私有的构造方法.*/
    private RedisUtil(){}
    
    /**
     * 检测REDIS是否可用
     * @return {@link Boolean}.
     */
    public static boolean isAvailable(){
    	return redisTemplate == null;
    }
    
    /**
     * 批量删除对应的value
     * @param keys
     */
    public static void remove(final String... keys) {
    	if(keys==null || keys.length <=0 || redisTemplate == null){
    		return;
    	}
    	Collection<Serializable> removeKeyList = Arrays.asList(keys);
    	redisTemplate.delete(removeKeyList);
    }
 
    /**
     * 批量删除key
     * 
     * @param pattern
     */
    public static void removePattern(final String pattern) {
    	if(redisTemplate == null){
    		return;
    	}
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }
 
    /**
     * 删除对应的value
     * 
     * @param key
     */
    public static void remove(final String key) {
    	if(redisTemplate == null){
    		return;
    	}
    	redisTemplate.delete(key);
    }
 
    /**
     * 判断缓存中是否有对应的value
     * 
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
    	if(redisTemplate == null){
    		return false;
    	}
        return redisTemplate.hasKey(key);
    }
 
    /**
     * 读取缓存
     * 
     * @param key
     * @return
     */
    public static Object get(final String key) {
    	if(redisTemplate == null){
    		return null;
    	}
        return redisTemplate.opsForValue().get(key);
    }
 
    /**
     * 根据key表达式查询所有的key
     * @param keyPattern key表达式
     * @return {@link Set}.
     */
    public static Set<Serializable> getPattern(final String keyPattern){
    	if(redisTemplate == null){
    		return null;
    	}
    	return redisTemplate.keys(keyPattern);
    }
    /**
     * 写入缓存(使用默认过期时间)
     * @param key 缓存KEY
     * @param value 缓存的值
     * @return 
     */
    public static boolean set(final String key, Object value) {
    	if(redisTemplate == null){
    		return false;
    	}
        try {
        	redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, getDefaultCacheExpireTime(), TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
        	LOG.error("[set exception||key="+key+",value="+value+",exceptionMsg="+e.getMessage()+"]");
        }
        return false;
    }
 
    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value, long expireTime) {
    	if(redisTemplate == null){
    		return false;
    	}
    	try{
    		redisTemplate.opsForValue().set(key, value);
    		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    	}catch(Exception e){
        	LOG.error("[set exception||key="+key+",value="+value+",expireTime="+expireTime+"]",e);
    	}
        return false;
    }
 
    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
    	if(RedisUtil.redisTemplate == null){
    		RedisUtil.redisTemplate = redisTemplate;
    	}
    }
    
	public static long getDefaultCacheExpireTime() {
		return defaultCacheExpireTime;
	}

	public void setDefaultCacheExpireTime(long defaultCacheExpireTime) {
		if(RedisUtil.defaultCacheExpireTime <= 0){
			RedisUtil.defaultCacheExpireTime = defaultCacheExpireTime;
		}
	}
	public static RedisTemplate<Serializable, Object> getRedisTemplate() {
		return redisTemplate;
	}
}