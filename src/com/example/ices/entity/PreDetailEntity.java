package com.example.ices.entity;

import java.util.List;

public class PreDetailEntity {
	//"id":6,"contentShortText":"你好吗","contentSortNumber":3,
	//"contentText":"秦舞阳碰见顾墨涵的时候，她正站在酒店走廊的窗边，望着窗外灰蒙蒙的天，听着外面淅淅沥沥的小雨，指间还燃着一只薄荷烟。今天是陪政府部门的官员来吃饭，吃了整整一个中午加半个下午，饭吃了，酒喝了，话也说了不少，直到三点多了，对方还没有要走的意思，秦舞阳突然感到烦躁，趁着借口去洗手间，就一直站在窗前，不知在想什么。助理小梁出来找她：“秦总，时间差不多了，沈处长他们要走了。”秦舞阳从思绪中回来，转头说声：“好。”准备回包间，这时身后传来嘈杂的说话声，秦舞阳不经意的一回头，心里紧了一下，脑中闪过一句话：有生之年，狭路相逢，终不能幸免。秦舞阳在心里狠狠的骂了句：“秦舞阳，你抖什么！不就是个男人吗！这些年你见的男人还少吗？”顾墨涵正被一群人拥着往外走，黑色的西装外套搭在手臂上，身上只穿了件普普通通的白色衬衣，却显得他更加玉树临风，在往上，棱角分明的面孔和五年前相比，多了几分成熟，白皙光滑的皮肤，粗而黑的眉毛，一双要飞入云鬓的桃花眼，挺直的鼻梁，薄薄的嘴唇，干净的下巴，如果在大街上碰见了，秦舞阳肯定会在心里赞一句：哇！青年才俊啊！",
	//"contentTitle":"你好6","createTime":null,"createUser":null,"directoryId":2,"pictures":[{"id":3,"createTime":null,"createUser":null,"foreignId":6,"foreignType":2,
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
