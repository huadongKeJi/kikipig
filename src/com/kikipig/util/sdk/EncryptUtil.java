package com.kikipig.util.sdk;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密工具类
 * 
 * @date 2017年7月1日10:17:54.
 */
public class EncryptUtil {

	private static final String UTF8 = "utf-8";

	/**
	 * MD5数字签名
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String md5Digest(String src) { // 定义数字签名方法, 可用：MD5, SHA-1
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(src.getBytes(UTF8));
			return byte2HexStr(b);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * BASE64编码
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String base64Encoder(String str) {
		try {
			return Base64.encodeBase64String(str.getBytes());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * BASE64解码
	 * 
	 * @param dest
	 * @return
	 * @throws Exception
	 */
	public static String base64Decoder(String str) {
		try {
			return new String(Base64.decodeBase64(str));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 字节数组转化为大写16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}
			sb.append(s);
		}
		return sb.toString().toUpperCase();
	}
}
