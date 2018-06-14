package com.kikipig.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类.
 * @date 2017年2月16日17:11:04.
 */
public class ThreadPoolUtil {

	/**
	 * 创建固定个数的线程池，达到6个时其余线程将等待.
	 */
	public static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);
	
}
