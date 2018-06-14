package com.kikipig.configure;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统常量配置.
 */
@Component
public class SystemConfigure {
	/** 程序异常通知邮件地址. */
	private static String exceptionSendEmailTo;
	/** 程序异常通知邮件标题. */
	private static String exceptionSendEmailSubject;
	/** 异常是否进行邮件通知.*/
	private static boolean notifyEmail;
	/**文件上传地址.*/
	private static String upload_path;
	/**PC项目地址.*/
	private static String pc_url;
	/**H5项目地址.*/
	private static String h5_url;
	/**
	 * 获取程序异常发送邮件通知地址
	 * @return
	 */
	public static String [] getExceptionSendEmailTo() {
		if(StringUtils.isBlank(exceptionSendEmailTo)){
			return null;
		}
		return exceptionSendEmailTo.split(",");
	}
	@Value("${exception_sendEmailList}")
	public void setExceptionSendEmailTo(String exceptionSendEmailTo) {
		if(StringUtils.isBlank(SystemConfigure.exceptionSendEmailTo)){
			SystemConfigure.exceptionSendEmailTo = exceptionSendEmailTo;
		}
	}
	public static boolean isNotifyEmail() {
		return notifyEmail;
	}
	@Value("${is_email_notify}")
	public void setNotifyEmail(boolean notifyEmail) {
		SystemConfigure.notifyEmail = notifyEmail;
	}

	public static String getExceptionSendEmailSubject() {
		return exceptionSendEmailSubject;
	}
	@Value("${exception_sendEmail_subject}")
	public void setExceptionSendEmailSubject(String exceptionSendEmailSubject) {
		if(StringUtils.isBlank(SystemConfigure.exceptionSendEmailSubject)){
			SystemConfigure.exceptionSendEmailSubject = exceptionSendEmailSubject;
		}
	}
	public static String getUpload_path() {
		return upload_path;
	}
	@Value("${upload_path}")
	public void setUpload_path(String upload_path) {
		if(StringUtils.isBlank(SystemConfigure.upload_path)){
			SystemConfigure.upload_path = upload_path;
		}
	}
	public static String getPc_url() {
		return pc_url;
	}
	@Value("${pc_url}")
	public void setPc_url(String pc_url) {
		if(StringUtils.isBlank(SystemConfigure.pc_url)){
			SystemConfigure.pc_url = pc_url;
		}
	}
	public static String getH5_url() {
		return h5_url;
	}
	
	@Value("${h5_url}")
	public void setH5_url(String h5_url) {
		if(StringUtils.isBlank(SystemConfigure.h5_url)){
			SystemConfigure.h5_url = h5_url;
		}		
	}
	private SystemConfigure(){}
}
