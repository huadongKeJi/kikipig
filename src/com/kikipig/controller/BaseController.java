package com.kikipig.controller;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kikipig.constant.ResultConstant;

public abstract class BaseController{
	protected static final String separator = "/";
	/**
	 * 输出JSON格式返回值
	 * 
	 * @param response
	 * @param resultData
	 *            需要返回的JSON数据.
	 */
	protected void writeJsonString(final HttpServletResponse response, final String resultData) {
		PrintWriter printWrite = null;
		try {
			response.setCharacterEncoding(ResultConstant.RESULT_CHARTSET);
			response.setContentType(ResultConstant.RESULT_CONTEXTTYPE_JSON);
			printWrite = response.getWriter();
			printWrite.write(resultData);
			printWrite.flush();
		} catch (Exception e) {
		//	Log.error("[writeJsonString exception||resultData=" + resultData + "]",e);
		} finally {
			if (printWrite != null) {
				printWrite.close();
			}
		}
	}
	/**
	 * 获取session
	 * @return {@link HttpSession}.
	 */
	protected HttpSession getSession(){
		return getRequest().getSession();
	}
	
	/**
	 * 获取request
	 * @return {@link ServletRequest}.
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}
