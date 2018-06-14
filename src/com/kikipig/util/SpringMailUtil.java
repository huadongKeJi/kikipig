package com.kikipig.util;

import java.util.Arrays;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 使用Spring的Mail 发送邮件
 * @date 2017年2月27日15:13:10.
 */
public class SpringMailUtil {

	/**日志记录.*/
	private static final Logger LOG = LogManager.getLogger(SpringMailUtil.class);
	private static JavaMailSenderImpl mailSender;
	private static SimpleMailMessage templateMessage;

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		SpringMailUtil.templateMessage = templateMessage;
	}
	
	public static void setMailSender(JavaMailSenderImpl mailSender) {
		SpringMailUtil.mailSender = mailSender;
	}

	/**
	 * 发送邮件
	 * @param subject 标题
	 * @param context 内容
	 * @param to 收件人.
	 */
	public static void send(final String subject, final String context, final String[] to) {
		LOG.info("[send request||subject="+subject+",to="+Arrays.toString(to)+"]");
		try{
			ThreadPoolUtil.fixedThreadPool.submit(new Runnable() {
				public void run() {
					final SimpleMailMessage mailMessage = new SimpleMailMessage(templateMessage);
					mailMessage.setSubject(subject);
					mailMessage.setTo(to);
					mailMessage.setText(context);
					mailMessage.setSentDate(new Date());
					mailSender.send(mailMessage);
				}
			});
		}catch(Exception e){
			LOG.error("[send exception||subject="+subject+",context="+context+",to="+Arrays.toString(to)+"]",e);
		}
	}
	
	/**
	 * 发送HTML格式的邮件
	 * @param subject 邮件标题
	 * @param context 邮件内容
	 * @param to 	    收件人数组.
	 */
	public static void sendHTMLEmail(final String subject, final String context, final String[] to){
		LOG.info("[sendHTMLEmail request||subject="+subject+",to="+Arrays.toString(to)+"]");
		ThreadPoolUtil.fixedThreadPool.submit(new Runnable() {
			public void run() {
				try {
					final JavaMailSenderImpl mailSenderClone = mailSender;
					final MimeMessage message = mailSenderClone.createMimeMessage();
					final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
					helper.setFrom(templateMessage.getFrom());
					helper.setTo(to);
					helper.setSubject(subject);
					helper.setText(context,true);
					helper.setSentDate(new Date());
					mailSenderClone.send(message);
				}catch (Exception e) {
					LOG.error("[sendHTMLEmail exception||subject="+subject+",context="+context+",to="+Arrays.toString(to)+"]",e);
				}
			}
		});
	}

	/** 禁止初始化后修改属性值. */
	private SpringMailUtil() {
	}
}