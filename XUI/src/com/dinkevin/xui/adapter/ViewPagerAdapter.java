package com.dinkevin.xui.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * XUILooperViewPager对应的数据源
 * @author chengpengfei
 */
public class ViewPagerAdapter extends PagerAdapter{
	
	protected List<View> viewList;
	protected List<String> titleList;
	
	/**
	 * 构造器
	 * @param viewList
	 */
	public ViewPagerAdapter(List<View> viewList){
		this.viewList = viewList;
	}
	
	/**
	 * 构造器
	 * @param viewList View 数组
	 * @param titleList 标题数组
	 */
	public ViewPagerAdapter(List<View> viewList,List<String> titleList){
		this.viewList = viewList;
		this.titleList = titleList;
	}
	
	public List<View> getDataSource(){
		return viewList;
	}
	
	@Override
	public void destroyItem(View view, int id, Object obj) {
		((ViewPager) view).removeView(viewList.get(id));
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (View)arg1;
	}
	
	@Override
	public Object instantiateItem(View view, int id) {
		((ViewPager) view).addView(viewList.get(id), 0);
		return viewList.get(id);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(null != titleList && titleList.size() > position){
			return titleList.get(position);
		}
		return super.getPageTitle(position);
	}
}
