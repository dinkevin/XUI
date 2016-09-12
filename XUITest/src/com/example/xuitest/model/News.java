package com.example.xuitest.model;

import java.util.List;

/**
 * 新闻资讯实体对象类
 * @author chengpengfei
 */
public class News {

	private String nid,title,url,site,abs,sourcets;
	private List<ImageUrls> imageurls;

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public List<ImageUrls> getImageurls() {
		return imageurls;
	}

	public void setImageurls(List<ImageUrls> imageurls) {
		this.imageurls = imageurls;
	}

	public String getSourcets() {
		return sourcets;
	}

	public void setSourcets(String sourcets) {
		this.sourcets = sourcets;
	}
}
