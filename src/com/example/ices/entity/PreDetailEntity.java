package com.example.ices.entity;

import java.util.List;

public class PreDetailEntity {
	//"id":6,"contentShortText":"�����","contentSortNumber":3,
	//"contentText":"������������ī����ʱ������վ�ھƵ����ȵĴ��ߣ����Ŵ�������ɵ��죬������������������С�ָ꣬�仹ȼ��һֻ�����̡����������������ŵĹ�Ա���Է�����������һ������Ӱ�����磬�����ˣ��ƺ��ˣ���Ҳ˵�˲��٣�ֱ��������ˣ��Է���û��Ҫ�ߵ���˼��������ͻȻ�е����꣬���Ž��ȥϴ�ּ䣬��һֱվ�ڴ�ǰ����֪����ʲô������С�����������������ܣ�ʱ�����ˣ��򴦳�����Ҫ���ˡ�����������˼���л�����תͷ˵�������á���׼���ذ��䣬��ʱ��������ӵ�˵�������������������һ��ͷ���������һ�£���������һ�仰������֮�꣬��·��꣬�ղ������⡣������������ݺݵ����˾䣺�����������㶶ʲô�������Ǹ���������Щ����������˻����𣿡���ī������һȺ��ӵ�������ߣ���ɫ����װ���״����ֱ��ϣ�����ֻ���˼�����ͨͨ�İ�ɫ���£�ȴ�Ե������������ٷ磬�����ϣ���Ƿ�������׺�����ǰ��ȣ����˼��ֳ��죬���⻬��Ƥ�����ֶ��ڵ�üë��һ˫Ҫ�������޵��һ��ۣ�ֱͦ�ı������������촽���ɾ����°ͣ�����ڴ���������ˣ��������϶�����������һ�䣺�ۣ�����ſ�����",
	//"contentTitle":"���6","createTime":null,"createUser":null,"directoryId":2,"pictures":[{"id":3,"createTime":null,"createUser":null,"foreignId":6,"foreignType":2,
	//"pictureLargeFilePath":"http://i.guancha.cn/news/2014/12/18/20141218101455857.jpg","pictureSmallFilePath":"http://i.guancha.cn/news/2014/12/18/20141218101455857.jpg","updateTime":null,"updateUser":null,"version":0}]
	private String contentTitle;
	private String urlTitle;
	private String urlPath;
	public String getUrlTitle() {
		return urlTitle;
	}
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	private String contentText;
	private List<PicturesEntity> pictures;
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public List<PicturesEntity> getPictures() {
		return pictures;
	}
	public void setPictures(List<PicturesEntity> pictures) {
		this.pictures = pictures;
	}
	
}
