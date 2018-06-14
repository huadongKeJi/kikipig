package com.kikipig.util;

import com.kikipig.constant.ResultConstant;

import net.sf.json.JSONObject;

/**
 * 返回的JSON格式工具类
 * @dae 2017年3月1日14:24:29
 * @author PHD.
 */
public class ResultJsonUtil {

	/**
	 * 构建返回的JSON对象
	 * @param resultCode 返回码
	 * @param resultReason 返回原因
	 * @return {@link JSONObject}.
	 */
	public static JSONObject buildResultJson(final String resultCode, final String resultReason){
		JSONObject result =new JSONObject(); //new JSONObject(); //返回的JSON格式数据
		result.accumulate(ResultConstant.RESULT_CODE_KEY, resultCode);
		result.accumulate(ResultConstant.RESULT_REASON_KEY, resultReason);
		return result;
	}
	/**
	 * 构建返回的JSON对象
	 * @param resultCode 返回码
	 * @param resultReason 返回原因
	 * @param resultData  返回数据
	 * @return {@link JSONObject}.
	 */
	public static JSONObject buildResultJson(final String resultCode, final String resultReason, final Object resultData){
		JSONObject result = new JSONObject(); //返回的JSON格式数据
		result.accumulate(ResultConstant.RESULT_CODE_KEY, resultCode);
		result.accumulate(ResultConstant.RESULT_REASON_KEY, resultReason);
		result.accumulate(ResultConstant.RESULT_DATA_KEY, resultData);
		return result;
	}
	/**
	 * 构建分页返回数据的JSON对象
	 * @param resultCode  返回码
	 * @param resultReason 返回原因
	 * @param resultData    返回的分页数据
	 * @param bean 分页信息
	 * @return {@link JSONObject}.
	 */

//	public static JSONObject buildPageListJson(final Object resultData,final PaginationBean bean){
//		
//	}
}
