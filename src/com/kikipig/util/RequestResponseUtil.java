package com.kikipig.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kikipig.constant.ResultConstant;

import net.sf.json.JSONObject;

/**
 * request,response 工具类
 * @date 2017年2月27日11:26:25.
 */
public class RequestResponseUtil {
	/** 日志记录. */
	private static final Logger LOG = LogManager.getLogger(RequestResponseUtil.class);

	/**
	 * 输出JSON数据
	 * @param response 响应的response
	 * @param jsonDataStr 响应的消息.
	 */
	public static void commonReturnMsg(final HttpServletResponse response, final String jsonDataStr) {
		PrintWriter printWrite = null;
		try {
			response.setCharacterEncoding(ResultConstant.RESULT_CHARTSET);
			response.setContentType(ResultConstant.RESULT_CONTEXTTYPE_JSON);
			printWrite = response.getWriter();
			printWrite.write(jsonDataStr);
			printWrite.flush();
		} catch (IOException e) {
			LOG.error("[commonReturnMsg exception||writeMessage=" + jsonDataStr + "]," + e);
		} finally {
			if (printWrite != null) {
				printWrite.close();
			}
		}
	}

	/**
	 * 通用的返回错误信息方法
	 * @param request 请求request
	 * @param response 响应的response
	 * @param message 响应的消息
	 * @param code 响应的code
	 * @param toHtmlUrl 如果不是ajax请求跳转的地址
	 * @throws Exception.
	 */
	public static void commonReturnMsg(final HttpServletRequest request, final HttpServletResponse response,
			final String message, final String code, final String toHtmlUrl) {
		PrintWriter printWrite = null;
		try {
			if (isAsyncRequest(request)) { // ajax异步请求返回统一的未登录数据
				response.addHeader(ResultConstant.OZUZU_HEADER_MESSAGE, message);
				response.addHeader(ResultConstant.OZUZU_HEADER_CODE, code);
				JSONObject writeJson = new JSONObject();
				writeJson.accumulate(ResultConstant.RESULT_CODE_KEY, code);
				writeJson.accumulate(ResultConstant.RESULT_REASON_KEY, message);
				response.setCharacterEncoding(ResultConstant.RESULT_CHARTSET);
				response.setContentType(ResultConstant.RESULT_CONTEXTTYPE_JSON);
				printWrite = response.getWriter();
				printWrite.write(writeJson.toString());
				printWrite.flush();
			} else {
				response.sendRedirect(request.getContextPath() + toHtmlUrl);
			}
		} catch (IOException e) {
			LOG.error("[commonReturnMsg exception||message=" + message + ",code=" + code + ",toHtmlUrl=" + toHtmlUrl+ "]" + e);
		} finally {
			if (printWrite != null) {
				printWrite.close();
			}
		}
	}

	/**
	 * 获取请求参数
	 * 
	 * @param map
	 *            请求参数Map
	 * @return {@link String}.
	 */
	public static String getParamString(final HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		StringBuilder result = new StringBuilder();
		for (Entry<String, String[]> e : paramMap.entrySet()) {
			result.append(e.getKey()).append("=");
			Object[] value = e.getValue();
			if (value != null && value.length == 1) {
				result.append(value[0]).append("\t");
			} else {
				result.append(Arrays.toString(value)).append("\t");
			}
		}
		return result.toString();
	}

	/**
	 * 验证是否是异步请求或者JSON格式
	 * @param request
	 * @return
	 */
	public static boolean isAsyncRequest(final HttpServletRequest request) {
		if(request != null){
			// update 防止获取accept为空出现空异常 2017年3月22日17:16:42.
			String header_accept = request.getHeader("accept");
			if((StringUtils.isNotBlank(header_accept) && header_accept.indexOf("application/json") > -1) || StringUtils.equals(request.getHeader("x-requested-with"),"XMLHttpRequest")){
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取完整的请求路径
	 * @param request 请求的HttpServletRequest
	 * @return {@link String}.
	 */
	public static String getAllUrlString(final HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + request.getServletPath();
	}
}
