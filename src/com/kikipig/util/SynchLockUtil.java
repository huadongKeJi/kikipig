package com.kikipig.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class SynchLockUtil {
	/**公用的对象同步锁Map.*/
	private static final Map<String, Object> lockMap = new HashMap<>(1000);
	/**
	 * 获取同步对象锁
	 * @param key 锁的key
	 * @return {@link Object}.
	 */
	public static synchronized Object getObject(final String key){
		if(StringUtils.isBlank(key)){
			return new Object();
		}
		if(lockMap.size() > 999){ //长度大于999清除所有的
			lockMap.clear();
		}
		Object result = lockMap.get(key);
		if(result == null){
			result = new Object();
			lockMap.put(key, result);
		}
		return result;
	}
	
	/**
	 * 删除同步对象锁
	 * @param key 锁的key
	 * @return {@link Object}.
	 */
	public static void removeObject(final String key){
		if(StringUtils.isBlank(key)){
			return;
		}
		Object result = lockMap.get(key);
		if(result != null){
			lockMap.remove(key);
		}
	}
}
