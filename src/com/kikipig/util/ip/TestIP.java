package com.kikipig.util.ip;

import java.io.IOException;


public class TestIP {

	public static void main(String[] args) {
		String IP = "219.131.61.81";  
        try {
        	String	dataFile ="D:/workWeb/wrok/mbrand/WebContent/ipData/QQWry.Dat";
        			/*TestIP.class.getClassLoader().getResource("/").getPath()
					.replace("/WEB-INF/classes/", "/ipData/QQWry.Dat");*/
			IPSeeker.I.init(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        String country = IPSeeker.I.getAddress(IP);  
        System.out.println(country);  
        if(country.indexOf("省")!=-1){
        	country=country.substring(country.indexOf("省")+1);  
        	 System.out.println(country);  
        }
        if(country.indexOf("市")!=-1){
        	country=country.substring(0,country.indexOf("市"));  
        	 System.out.println(country);  
        }
	}
} 