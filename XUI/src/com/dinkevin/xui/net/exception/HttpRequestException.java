package com.dinkevin.xui.net.exception;

/**
 * 网络请求异常
 * @author chengpengfei
 */
public class HttpRequestException extends Exception{

	private static final long serialVersionUID = 4446729250895263769L;

	private int code;
	private String message;
	private Throwable throwable;
	
	public HttpRequestException(int code,String message){
		this(code,message,null);
	}
	
	public HttpRequestException(int code,String message, Throwable throwable){
		this.code = code;
		this.message = message;
		this.throwable = throwable;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public int getCode(){
		return code;
	}
	
	public Throwable getThrowable(){
		return throwable;
	}

	@Override
	public String toString() {
		return "code:"+code+" message:"+message;
	}
}
