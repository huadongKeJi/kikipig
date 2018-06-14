package com.kikipig.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 字符工具类
 * @date 2017年6月15日11:44:50.
 */
public class StringUtil {

	/**
	 * 设置手机号码 中间4位 *
	 * @param mobile
	 * @return
	 */
	public static String setMobile(String mobile){
		if(StringUtils.isBlank(mobile))
			return "";
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}
	/**
	 * 生成用户密码盐
	 * @return {@link String}.
	 */
	public static String getPwdSalt(){
		return RandomStringUtils.randomAlphanumeric(6);
	}
	
	/**生成随机订单号(20位).*/
	public static String getTradeNo(){
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss").concat(RandomStringUtils.randomNumeric(6));
	}
	/**
	 * 
	 * 转换double为String，保留两位小数
	 * @param value
	 * @return
	 */
	public final static String doubleToString(final double value){
		DecimalFormat myformat=new DecimalFormat("0.00");
		return myformat.format(value);
	}
	
	/**
	 * 验证密码是否符号规则      数字大小写字母特殊字符
	 * @param pwd
	 * @return
	 */
	public static boolean isPwd(String pwd){
		Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}$");
	    return p.matcher(pwd).matches();
	}
	public static void main(String[] args) {
		System.err.println(isPwd("Aphd"));
		
	}
	
	 /**
	  * 隐藏手机号中间四位
	  * @param phone 需要隐藏的手机号
	  * @return {@link String}.
	  */
	 public static String hidePhone(final String phone){
		 if(StringUtils.isBlank(phone) || phone.length() < 11){
			 return phone;
		 }
		 return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	 }
	 /**
	  * 生成竞价号
	  * @return
	  */
	 public static String getBidCode(){
		 String[] letter = {"Z","A","Q","W","S","X","E","D","C","R","F","V","T","G","B","Y","H","N"
				 ,"U","J","K","M","L","P"};
		 int index = (int)(Math.random()*23);
		 String bidCode = letter[index];
		 String[] number ={"1","3","5","7","8","9","0"};
		 for (int i = 0; i < 4; i++) {
			 index = (int)(Math.random()*6);
			 bidCode=bidCode.concat(number[index]);
		}
		 return bidCode;
	 }
	/* public static void main(String[] args) {
		System.err.println(getBidCode()+","+Math.random()*6);
	}
	 */
	   /**
	     * 将传过来的异常类写入到io流中,通过流来获取堆栈异常信息写入到log4j文件
	     * @param t 异常类
	     * @return String 异常信息
	     */
	    public static String getTrace(Throwable t) {
			StringWriter stringWriter= new StringWriter();
			PrintWriter writer= new PrintWriter(stringWriter);
			//将异常的信息写入到流里
			t.printStackTrace(writer);
			//从流中获取异常信息
			StringBuffer buffer= stringWriter.getBuffer();
			return buffer.toString();
		}
	    
    /**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(final String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

}
