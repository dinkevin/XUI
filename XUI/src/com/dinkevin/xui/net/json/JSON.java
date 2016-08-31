package com.dinkevin.xui.net.json;

/**
 * JSON 数据格式服务器返回对象实体类
 * @author chengpengfei
 */
public class JSON {

	private int code; // 服务器返回代码
	private String msg; // 服务器返回的消息
	private String data;	// 数据
	
	/**
	 * 服务器返回代码正确
	 */
	public final static int CODE_OK = 1000;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
