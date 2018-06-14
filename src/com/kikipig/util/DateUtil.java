package com.kikipig.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 时间工具类
 * @date 2017年06月19日10:51:04
 * @author panhaidong.
 *
 */
public class DateUtil {
	/**默认的时间格式.*/
	public final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static Date getDateByString(final String dateStr, String pattern){
		if(StringUtils.isBlank(dateStr)){
			return null;
		}
		Date result = null;
		try {
			result = DateUtils.parseDate(dateStr, StringUtils.isBlank(pattern) ? DEFAULT_PATTERN: pattern);
		} catch (ParseException e) {
			
		}
		return result;
	}
	
	/**
	 * 时间加减
	 * @param date 日期
	 * @param addTime  毫秒数
	 * @return
	 */
	public static Date addDate(Date date,long addTime){
		 Date afterDate = new Date(new Date().getTime() + addTime);
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 System.out.println(sdf.format(afterDate ));
		 return afterDate;
	}
	
	/**
	 * 获取前一天 上午 10 点
	 * @param date
	 * @return
	 */
	public static Date getBeforeDay(Date date){
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		 /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 System.out.println(sdf.format(calendar.getTime() ));*/
		return calendar.getTime() ; 
	} 
	public static void main(String[] args) {
//		addDate(new Date(),-300000);//5分钟
//		addDate(new Date(),-1800000);//30分钟
//		addDate(new Date(),-7200000);//两个小时
//		System.out.println(getBeforeDay(new Date()));//前一天的上午10点
		System.out.println(calcCurrentDaySurplusSecond());
		
	}
	private static final String date_format_minut = "yyyy-MM-dd HH:mm";
	/**
	 * 获取精确到分钟的时间格式
	 * @param date 时间
	 * @return {@link String}.
	 */
	public static String getDateStrFormat_minute(final Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(date_format_minut);
		return dateFormat.format(date);
	}
	
	public static long calcCurrentDaySurplusSecond(){
		Date currentDate = new Date();
		Date lastDate = DateUtils.setHours(currentDate, 23);
		lastDate = DateUtils.setMinutes(lastDate, 59);
		lastDate = DateUtils.setSeconds(lastDate, 59);
		System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(currentDate));
		System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(lastDate));
		return (lastDate.getTime() - currentDate.getTime())/1000;
	}
}
