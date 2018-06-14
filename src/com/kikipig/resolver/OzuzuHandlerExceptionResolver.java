package com.kikipig.resolver;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.kikipig.configure.SystemConfigure;
import com.kikipig.constant.ResultConstant;
import com.kikipig.constant.SessionConstant;
import com.kikipig.util.AddressUtils;
import com.kikipig.util.RequestResponseUtil;
import com.kikipig.util.SpringMailUtil;

public class OzuzuHandlerExceptionResolver extends SimpleMappingExceptionResolver{
	/**日志记录.*/
	private final Logger LOG = LogManager.getLogger(OzuzuHandlerExceptionResolver.class);
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) {
		final String requestUrl = RequestResponseUtil.getAllUrlString(request);   //获取请求的完整路径
		final String requestParams = RequestResponseUtil.getParamString(request); //获取请求的全部参数
		final String exceptionInfo = getErrorInfoFromException(ex);				//获取异常信息
		LOG.error("[doResolveException requestUrl="+requestUrl+",params:"+requestParams+"]",ex);
		String [] to = SystemConfigure.getExceptionSendEmailTo(); //异常信息邮件通知地址.
		if(ArrayUtils.isEmpty(to) && SystemConfigure.isNotifyEmail()){ //邮件地址如果为空 或者长度少于1 记录异常
			LOG.error("[exception notify fail||notify mail address be not null]");
		}else if(SystemConfigure.isNotifyEmail()){ //允许发送邮件通知
			String exceptionEmailContent = buildExceptionEmailContent(exceptionInfo, requestUrl, requestParams, request.getSession().getAttribute(SessionConstant.SESSION_USER_KEY), AddressUtils.getIpAddress(request));
			final String subject = "["+SystemConfigure.getExceptionSendEmailSubject()+"异常通知-"+ex.toString()+"]"; //邮件标题
			SpringMailUtil.sendHTMLEmail(subject, exceptionEmailContent.toString(), to); //发送邮件通知技术人员
		}
		if(RequestResponseUtil.isAsyncRequest(request)){
			RequestResponseUtil.commonReturnMsg(request, response, ResultConstant.OZUZU_HEADER_MESSAGE_SERVER_ERROR, ResultConstant.OZUZU_HEADER_CODE_SERVER_ERROR, "WEB-INF/view/front/error/500");
			return null;
		}
		return new ModelAndView("/error");
	}
	
	/**
	 * 构建异常通知邮件内容
	 * @param exceptionInfo 异常信息
	 * @param requestUrl	请求地址
	 * @param requestParams	请求参数
	 * @param session_obj	当前session用户
	 * @param ip			请求的IP地址
	 * @return {@link String}.
	 */
	private String buildExceptionEmailContent(final String exceptionInfo,final String requestUrl, final String requestParams, final Object session_obj,final String ip){
		final StringBuffer context = new StringBuffer();
		context.append("<html><body>");
		context.append("<h1>["+SystemConfigure.getExceptionSendEmailSubject()+"]程序出现异常,异常信息如下：</h1>");
		context.append("<p>请求的地址："+requestUrl+"</p>");
		context.append("<p>请求的参数："+requestParams+"</p>");
		if(session_obj != null){
//			SysUser session_user = (SysUser) session_obj;
//			context.append("<p>当前用户名称："+session_user.getUserName()+",角色："+session_user.getRole()+"</p>");
		}else{
			context.append("<p>当前用户未登录</p>");
		}
		context.append("<p>请求的IP地址："+ip+"</p>");
		context.append(exceptionInfo.replace("\n","<br>&nbsp;&nbsp;&nbsp;&nbsp;")); //替換 /b html <br>标签
		context.append("<p>请大神尽快解决问题 (*^__^*)!</p>");
		context.append("</body></html>");
		return context.toString();
	}
	
	private String getErrorInfoFromException(Exception e) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			return "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e2) {
			return "ErrorInfoFromException";
		}finally{
			if(pw != null){
				pw.close();
			}
			if(sw != null){
				try {
					sw.close();
				} catch (IOException e1) {
				}
        	}
        }
    }
}