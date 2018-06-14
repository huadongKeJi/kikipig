package com.kikipig.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则表达式工具类
 * @date 2017年6月15日09:27:23.
 */
public class RegularUtil {
	
	/**
	 * 检查是不是符合为合法的中国的手机号码
	 * @param mobiles 手机号码
	 * @return {@link Boolean}.
	 */
    public static boolean isMobileNO(final String mobiles) {
        if (StringUtils.isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        return p.matcher(mobiles).matches();
    }
    
    /**
     * 是不是合法的登陆用户名
     * @param userName
     * @return
     */
    public static boolean isUserName(final String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }
       /* Pattern p = Pattern.compile("/[A-Za-z][A-Za-z0-9_@.]{6,20}/");
        return p.matcher(userName).matches();*/
        return true;
    }
}
