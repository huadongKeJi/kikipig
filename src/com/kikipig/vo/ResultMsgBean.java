package com.kikipig.vo;

import java.io.Serializable;
/**
 * 返回消息bean
 * @date 2017年2月13日17:50:02.
 */
public class ResultMsgBean implements Serializable{
	private static final long serialVersionUID = -8600492137028639810L;
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	/**返回的code.*/
	private int code;
	/**返回的消息.*/
	private String msg;
	/**返回的数据.*/
	private Object data;
	
	public ResultMsgBean() {
		super();
	}
	
	/**
	 * 错误消息返回构造方法
	 * @param msg 错误消息.
	 */
	public ResultMsgBean(String msg) {
		this.code = ResultMsgBean.FAIL;
		this.msg = msg;
	}

	public ResultMsgBean(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResultMsgBean(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultMsgBean [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}
