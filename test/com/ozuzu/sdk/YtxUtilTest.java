package com.ozuzu.sdk;

import org.junit.Test;

import com.kikipig.util.sdk.YtxUtil;

public class YtxUtilTest {
	
	@Test
	public void sendSmsTest(){
		System.out.println(YtxUtil.sendSms("105247",new String[]{"666666","6"}, "13728825820"));;
	}
}
