package com.kikipig.constant;
/**
 * 返回信息的常量类
 * @date 2017年2月27日11:29:29.
 */
public class ResultConstant {
	/**园租租 头部消息key.*/
	public static final String OZUZU_HEADER_MESSAGE = "ozuzu_header_message";
	/**园租租头部code代码key.*/
	public static final String OZUZU_HEADER_CODE = "ozuzu_header_code";
	/**用户未登录返回消息.*/
	public static final String OZUZU_HEADER_MESSAGE_NOLOGIN = "Not logged in";
	/**用户未登录返回code.*/
	public static final String OZUZU_HEADER_CODE_NOLOGIN = "222222";
	/**角色权限不足返回消息.*/
	public static final String OZUZU_HEADER_MESSAGE_ROLE_DENIED = "Role Insufficient permissions";
	/**角色权限不足返回code.*/
	public static final String OZUZU_HEADER_CODE_ROLE_DENIED = "43333";
	/**服务器错误返回信息.*/
	public static final String OZUZU_HEADER_MESSAGE_SERVER_ERROR = "Server Error";
	/**服务器错误返回code.*/
	public static final String OZUZU_HEADER_CODE_SERVER_ERROR = "50000";
	/**返回的code字段 key.*/
	public static final String RESULT_CODE_KEY = "code";
	/**返回的reason字段key.*/
	public static final String RESULT_REASON_KEY = "reason";
	/**返回的数据data字段key.*/
	public static final String RESULT_DATA_KEY = "data";
	/**返回数据data字段中的总页数.*/
	public static final String RESULT_DATA_PAGENUM_KEY = "pageNum";
	/**返回数据data字段中的当前页.*/
	public static final String RESULT_DATA_PAGECOUNT_KEY = "pageCount";
	/**返回数据data字段中list.*/
	public static final String RESULT_DATA_LIST_KEY = "list";
	/**返回信息的字符编码.*/
	public static final String RESULT_CHARTSET = "UTF-8";
	/**返回的内容格式：JSON.*/
	public static final String RESULT_CONTEXTTYPE_JSON = "application/json; charset=utf-8";
	
	/**参错错误返回码.*/
	public static final String RESULT_CODE_INVALID_PARAMS = "40003";
	/**参数错误返回原因.*/
	public static final String RESULT_REASON_INVALID_PARAMS = "请求参数错误";
	/**请求成功返回码.*/
	public static final String RESULT_CODE_SUCCESS = "20000";
	/**请求失败返回码.*/
	public static final String RESULT_CODE_FAIL = "40000";
	/**请求重复返回码.**/
	public static final String RESULT_CODE_REPEAT = "333333";
	/**请求不存在返回码.*/
	public static final String RESULT_CODE_NOTFOUND = "400004";
	/**请求没有更多数据返回码.*/
	public static final String RESULT_CODE_NOMORE = "400001";
	public static final String result_code_request_exception = "302";
	
	/**验证码处于有效期内.*/
	public static final String RESULT_CODE_NOTSEND = "40005";
	
	public static final String OZUZU_REGISTER_CODE_KEY="auction_code_isvlidata";
	
	/**已成功缴纳保证金*/
	public static final int ENTER_IS_DEPOSITPRICE_YES=2;
	
	/**已成功缴纳保证金 管理员 未审核*/
	public static final int ENTER_MANAGE_NOT_AUDIT=3;
	/**报名成功未缴纳保证金*/
	public static final int ENTER_IS_DEPOSITPRICE_NO=1;
	
	/**用户未报名*/
	public static final int ENTER_NO_ENTER=0;
	/**获取虚拟账号失败状态*/
	public static final int ENTER_NOTGET_ACCOUNT=4;
	
	
}
