package com.example.ices.entity;

import java.util.List;

public class MessageDetailEntity {
	//"id":2,"createTime":null,
	//"createUser":"王敏",
	//"eventsId":0,
	//"notificationContent":"望着窗外灰蒙蒙的天，听着外面淅淅沥沥的小雨",
	//"notificationShortContent":"望着窗外灰蒙蒙的天","notificationTitle":"普通通知标题2",
	//"notificationType":1,
	//"pictures":[{"id":5,"createTime":null,"createUser":null,"foreignId":2,"foreignType":1,"pictureLargeFilePath":"http://img.beelink.com/FileUpload/2014/12/17/141217160312711.jpg","pictureSmallFilePath":"http://img.beelink.com/FileUpload/2014/12/17/141217160312711.jpg","updateTime":null,"updateUser":null,"version":0
	private int id;
	private String createTime;
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	private String notificationTitle;
	
	public String getNotificationTitle() {
		return notificationTitle;
	}
	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}
	private int eventsId;
	private String notificationContent;
	private String notificationShortContent;
	private int notificationType;//1 普通。。2活动
	private List<PicturesEntity>pictures;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEventsId() {
		return eventsId;
	}
	public void setEventsId(int eventsId) {
		this.eventsId = eventsId;
	}
	public String getNotificationContent() {
		return notificationContent;
	}
	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}
	public String getNotificationShortContent() {
		return notificationShortContent;
	}
	public void setNotificationShortContent(String notificationShortContent) {
		this.notificationShortContent = notificationShortContent;
	}
	public int getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}
	public List<PicturesEntity> getPictures() {
		return pictures;
	}
	public void setPictures(List<PicturesEntity> pictures) {
		this.pictures = pictures;
	}
	
}
