package com.dinkevin.xui.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Iterator;

import com.dinkevin.xui.net.exception.HttpRequestException;
import com.dinkevin.xui.util.Debuger;
import com.dinkevin.xui.util.FileUtil;

import android.text.TextUtils;

/**
 * 同步方式 HTTP 网络请求封装类
 * 
 * @author chengpengfei
 */
public class SyncHttpRequest {

	private SyncHttpRequest() {}

	/**
	 * 生成一个网络请求封装类
	 * @return
	 */
	public static SyncHttpRequest create() {
		return new SyncHttpRequest();
	}

	private int timeout = 5000; // 连接超时时间，单位为毫秒

	/**
	 * 设置网络连接超时时间
	 * @param timeout 只有大于等于 1000 时才有效，单位为毫秒
	 */
	public void setTimeout(int timeout) {
		if (timeout < 1000)
			return;
		this.timeout = timeout;
	}

	/**
	 * 错误码：请求地址格式错误
	 */
	public final static int ERROR_CODE_ACTION_FORMAT = -1;

	/**
	 * 打开网络连接失败
	 */
	public final static int ERROR_CODE_CONNECT_FAILED = -2;

	/**
	 * 错误码：打开网络输入流错误
	 */
	public final static int ERROR_CODE_OPEN_INPUT_STREAM = -3;
	
	/**
	 * 错误码：打开网络输出流错误
	 */
	public final static int ERROR_CODE_OPEN_OUTPUT_STREAM = -4;
	
	/**
	 * 错误码：写入网络输出流错误
	 */
	public final static int ERROR_CODE_WRITE_OUTPUT_STREAM = -5;

	/**
	 * 错误码：读取网络数据错误
	 */
	public final static int ERROR_CODE_READ_INPUT_STREAM = -6;

	/**
	 * 执行 HTTP GET 请求
	 * @param action 请求地址
	 * @return 服务器返回数据
	 * @throws HttpRequestException
	 */
	public String get(String action) throws HttpRequestException {
		return get(action, null);
	}

	/**
	 * 执行 HTTP GET 请求
	 * @param action 请求地址
	 * @param headers 头数据
	 * @return 服务器返回数据
	 * @throws HttpRequestException
	 */
	public String get(String action, Headers headers) throws HttpRequestException {

		// 网络请求地址格式验证
		URL url = null;
		try {
			url = new URL(action);
		} catch (MalformedURLException e) {
			throw new HttpRequestException(ERROR_CODE_ACTION_FORMAT, "请求网络地址格式错误", e.getCause());
		}

		Debuger.d("get url -> ",action);
		if(null != headers) Debuger.d("get header -> ",headers.toString());
		
		// 打开网络连接
		URLConnection connection = null;
		try {
			connection = url.openConnection();
		} catch (IOException e) {
			throw new HttpRequestException(ERROR_CODE_CONNECT_FAILED, "打开网络连接失败", e.getCause());
		}

		// 设置超时
		connection.setConnectTimeout(timeout);

		// 添加头信息
		if (null != headers) {
			Iterator<String> it = headers.container.keySet().iterator();
			while (it.hasNext()) {
				String field = it.next();
				String value = headers.container.get(field);
				connection.addRequestProperty(field, value);
			}
		}

		// 添加默认的头
		connection.setRequestProperty("Accept", "*/*");
		connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");

		// 打开网络输入流
		InputStream input = null;
		try {
			input = connection.getInputStream();
		} catch (IOException e) {
			throw new HttpRequestException(ERROR_CODE_OPEN_INPUT_STREAM, "打开输入流失败", e.getCause());
		}

		// 读取数据
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String result = "", line;
		try {
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			Debuger.d("get output -> ",result);
		} catch (IOException e) {
			throw new HttpRequestException(ERROR_CODE_OPEN_INPUT_STREAM, "读取输入流失败", e.getCause());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileUtil.closeStream(input);
		return result;
	}

	/**
	 * 执行 HTTP POST 请求
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @return 服务器返回数据
	 * @throws HttpRequestException
	 */
	public String post(String action,Params params) throws HttpRequestException{
		return post(action, params, null);
	}
	
	/**
	 * 执行 HTTP POST 请求
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @param headers 头数据
	 * @return 服务器返回数据
	 * @throws HttpRequestException
	 */
	public String post(String action, Params params,Headers headers) throws HttpRequestException {
		
		// 网络请求地址格式验证
		URL url = null;
		try {
			url = new URL(action);
		} catch (MalformedURLException e) {
			throw new HttpRequestException(ERROR_CODE_ACTION_FORMAT, "请求网络地址格式错误", e.getCause());
		}
		
		Debuger.d("post url -> ",action);
		if(null != headers) Debuger.d("post header -> ",headers.toString());
		if(null != params) Debuger.d("post params -> ",params.toString());

		// 打开网络连接
		URLConnection connection = null;
		try {
			connection = url.openConnection();
		} catch (IOException e) {
			throw new HttpRequestException(ERROR_CODE_CONNECT_FAILED, "打开网络连接失败", e.getCause());
		}

		// 设置超时
		connection.setConnectTimeout(timeout);

		// 添加头信息
		if (null != headers) {
			Iterator<String> it = headers.container.keySet().iterator();
			while (it.hasNext()) {
				String field = it.next();
				String value = headers.container.get(field);
				connection.addRequestProperty(field, value);
			}
		}

		// 添加默认的头
		connection.setRequestProperty("Accept", "*/*");
		connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		
		// 发送POST请求必须设置如下两行
		connection.setDoOutput(true);
		connection.setDoInput(true);
		
		// 打开网络输出流
		OutputStream output = null;
		try{
			output = connection.getOutputStream();
		} catch(IOException e){
			throw new HttpRequestException(ERROR_CODE_OPEN_OUTPUT_STREAM, "打开网络输出流失败",e.getCause());
		}
		
		// 写入数据
		if(params != null){
			String paramsString = params.toString();
			if(!TextUtils.isEmpty(paramsString)){
				byte[] data = paramsString.getBytes(Charset.forName("UTF-8"));
				try{
					output.write(data);
				} catch(IOException e){
					throw new HttpRequestException(ERROR_CODE_WRITE_OUTPUT_STREAM, "写入网络数据流异常",e.getCause());
				}
			}
		}

		// 打开网络输入流
		InputStream input = null;
		try {
			input = connection.getInputStream();
		} catch (IOException e) {
			throw new HttpRequestException(ERROR_CODE_OPEN_INPUT_STREAM, "打开输入流失败", e.getCause());
		}

		// 读取数据
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String result = "", line;
		try {
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			Debuger.d("post output -> ",result);
		} catch (IOException e) {
			throw new HttpRequestException(ERROR_CODE_OPEN_INPUT_STREAM, "读取输入流失败", e.getCause());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileUtil.closeStream(input);
		return result;
	}
}
