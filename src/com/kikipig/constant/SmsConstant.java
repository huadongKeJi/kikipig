package com.kikipig.constant;

/**
 * 短信常量
 * @date 2017年2月21日15:10:10.
 */
public class SmsConstant {

	/**
	 * 发送状态：成功.
	 */
	public static final int SEND_STAUTS_SUCCESS = 1;
	/**
	 * 发送状态：失败.
	 */
	public static final int SEND_STATUS_FAIL = 2;
	
	/**
	 * 发送状态：待发送.
	 */
	public static final int SEND_STATUS_WAIT = 3;
	
	/**
	 * 短信类型：园区通.
	 */
	public static final int SMS_TYPE_ZST = 1;
	
	/**
	 * 短信类型:平台通知.
	 */
	public static final int SMS_TYPE_NOTICE = 2;
	
	/**当天手机验证码发送最大次数.**/
	public static final int VALID_SMS_MAX_COUNT = 16;
	/**云通讯验证码模板ID.*/
	public static final String YTX_VALID_CODE_ID = "105247";
	/**云之讯验证码模板ID.*/
	public static final String YZX_VALID_CODE_ID = "85650";
}
