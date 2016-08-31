package com.dinkevin.xui.module;

import java.util.Comparator;

/**
 * 自定义Model排序
 * @author chengpengfei
 *
 * @param <T>
 */
public class ModelComparator implements Comparator<BaseModel>{

	@Override
	public int compare(BaseModel lhs, BaseModel rhs) {
		return lhs.getId() - rhs.getId();
	}
}
