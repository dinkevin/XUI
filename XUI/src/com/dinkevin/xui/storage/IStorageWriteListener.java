package com.dinkevin.xui.storage;

/**
 * 数据存储写入监听
 * @author chengpengfei
 *
 */
public interface IStorageWriteListener {

	/**
	 * 写入结束回调
	 * @param localFilePath 写入成功的本地数据路径
	 */
	void onWriteComplete(String localFilePath);
	
	/**
	 * 写入错误回调
	 * @param errorCode
	 * @param errorMessage
	 */
	void onWriteError(int errorCode,String errorMessage);
}
