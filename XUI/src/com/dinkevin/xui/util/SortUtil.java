package com.dinkevin.xui.util;

import java.util.Collections;
import java.util.List;
import com.dinkevin.xui.module.BaseModel;
import com.dinkevin.xui.module.ModelComparator;

/**
 * 排序工具类
 * @author chengpengfei
 */
public final class SortUtil {

	private SortUtil(){}
	
	/**
	 * 排序
	 * @param data
	 * @return
	 */
	public static void sort(List<? extends BaseModel> data){
		if(data != null)
		{
			ModelComparator comparator = new ModelComparator();
			Collections.sort(data,comparator);
		}
	}
}
