package com.kikipig.util;

public class MapHouseUtil {

	public static String gbEncoding(final String gbString) { //将中文转成unicode码
		char[] utfBytes = gbString.toCharArray();  
		String unicodeBytes = ""; 
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) { 
			String hexB = Integer.toHexString(utfBytes[byteIndex]); 
			if (hexB.length() <= 2) {  
				hexB = "00" + hexB; 
			}   
			unicodeBytes = unicodeBytes + "\\u" + hexB;   
		}   
		return unicodeBytes; 
	} 
}
