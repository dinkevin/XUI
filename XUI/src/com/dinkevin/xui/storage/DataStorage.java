package com.dinkevin.xui.storage;

import java.io.File;
import com.dinkevin.xui.coder.DESCoder;
import com.dinkevin.xui.util.Debuger;
import com.dinkevin.xui.util.FileUtil;
import com.dinkevin.xui.util.StringUtil;
import android.text.TextUtils;

/**
 * 加密数据存储管理</br>
 * 此 Storage 中的数据存储经过加密处理，读取的时候经过解密得到原数据。</br>
 * 因为加解密数据需要时间，所以不要存储过大的数据内容。</br>
 * 调用初始化 {@link #initial(String)} 后，如果想设置新的密钥，可以调用 {@link #setEncoderKey(String)} 设置新密钥。
 * @author chengpengfei
 */
public class DataStorage extends Storage {

	public static final String SUFFIX = ".data";
	
	protected DataStorage(){
		super();
	}
	
	private static DataStorage singleton = new DataStorage();
	public static DataStorage getInstance(){
		return singleton;
	}
	
	/**
	 * 密钥
	 */
	private String encoderKey = "xiaoqi2016"; 	// 默认的密钥
	
	/**
	 * 设置数据加解密的密钥
	 * @param encoderKey 长度不小于8
	 * @return 
	 */
	public boolean setEncoderKey(String encoderKey){
		if(StringUtil.isEmpty(encoderKey) || encoderKey.length() < 8){
			return false;
		}
		
		this.encoderKey = encoderKey;
		return true;
	}
	
	/**
	 * 存储数据
	 * @param uid 数据唯一标识
	 * @param data	数据
	 * @return
	 */
	public boolean put(String uid,byte[] data){
		
		if(data == null) return false;
		String localFilePath = storageRootPath + uid + SUFFIX;
		FileUtil.deleteFile(localFilePath);
		try {
			byte[] encodeData = DESCoder.encrypt(data, encoderKey);
			return FileUtil.writeToFile(encodeData, 0, encodeData.length, localFilePath);
		} catch (Exception e) {
			Debuger.e("putData 数据加密处理异常",e.getMessage());
			return false;
		}
	}
	
	/**
	 * 读取数据
	 * @param uid
	 * @return
	 */
	public byte[] get(String uid){
		
		if(TextUtils.isEmpty(uid)) return null;
		String localFilePath = storageRootPath + uid + SUFFIX;
		
		File file = new File(localFilePath);
		if(file.exists()){
			long length = file.length();
			if(length > Integer.MAX_VALUE){
				Debuger.e(localFilePath,"文件内容过大，不能放进 byte[]中");
				return null;
			}
			
			byte[] buffer = new byte[(int)length];
			if(FileUtil.readFileData(localFilePath, buffer, 0) > 0){
				try {
					byte[] decodeData = DESCoder.decrypt(buffer,encoderKey);
					return decodeData;
				} catch (Exception e) {
					Debuger.e("putData 数据解密处理异常",e.getMessage());
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public void remove(String uid) {
		remove(storageRootPath,uid, SUFFIX);
	}
}
