package cn.edu.shu.scommunity.model;

import java.io.Serializable;

public class ActivityBean implements Serializable{
	private String OUT_LINE;	//概要
	private String IMG_URL;		//图片地址
	private String CONTENT;		//内容
	private String NEWS_ID;		//信息id
	private String TITLE;		//标题
	public ActivityBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActivityBean(String messageId, String title, String outline, String content
			, String img) {
		super();
		this.OUT_LINE = outline;
		this.IMG_URL = img;
		this.CONTENT = content;
		this.NEWS_ID = messageId;
		this.TITLE = title;
	}
	public String getOUT_LINE() {
		return OUT_LINE;
	}
	public void setOUT_LINE(String oUT_LINE) {
		OUT_LINE = oUT_LINE;
	}
	public String getIMG_URL() {
		return IMG_URL;
	}
	public void setIMG_URL(String iMG_URL) {
		IMG_URL = iMG_URL;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getNEWS_ID() {
		return NEWS_ID;
	}
	public void setNEWS_ID(String nEWS_ID) {
		NEWS_ID = nEWS_ID;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	
	
	
	
	
	
}
