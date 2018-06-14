
package com.kikipig.constant;
/**
 * REDIS常量类
 * @date 2017年6月17日09:17:28.
 */
public class RedisConstant {

	/**用户登录KEY.*/
	public static final String USER_LOGIN_KEY = "jp_user_login_key_";
	
	/**缓存时间：一天.*/
	public static final long DATE_ONE_DAY = 86400;
	/**缓存时间:一个小时*/
	public static final long DATE_ONE_HOURS=3600;
	/**竞拍项目竞价详情*/
	public static final String PROJECT_BID_INFO_KEY = "jp_project_bid_info_key_";
	/**当天某个号码发送短信验证码次数.*/
	public static final String SEND_SMS_COUNT_KEY = "jp_sms_count_key_";
}
