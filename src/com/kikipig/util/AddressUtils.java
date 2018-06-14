package com.kikipig.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.kikipig.util.ip.IPSeeker;



/**
 * 根据IP地址获取详细的地域信息
 */
public class AddressUtils {
	/**
	 * 根据request请求来获取ip地址
	 * 
	 * @param request
	 * @return ip
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 根据ip来编码格式,来获取省份,市以及区域
	 * 
	 * @param ip
	 * @return String[] array
	 * @throws UnsupportedEncodingException
	 */

	public static String getAddress(String ip){
		try {
			ip = ip.replace("ip=", "");
			if (ip.contains(",")) {
				ip = ip.split(",")[0];
			}
			return IPSeeker.I.getAddress(ip);
		} catch (Exception e) {
			return "广东省深圳市";// 请求错误
		}
	}
}