package com.example.ices.entity;

public class NotifiEntity {
	//"result":[{"id":27,"notificationIsRead":1,
//	"notificationId":11,"pictureSmallFilePath":null,
//	"notificationType":2,"eventsId":12,
//	"notificationShortContent":"望着窗外灰蒙蒙的天","notificationTitle":"活动通知标题11"}]}
	private int id;
	private int notificationIsRead;
	private int notificationId;
	private String pictureSmallFilePath;
	private int notificationType;
	private int eventsId;
	private String notificationShortContent;
	private String notificationTitle;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNotificationIsRead() {
		return notificationIsRead;
	}
	public void setNotificationIsRead(int notificationIsRead) {
		this.notificationIsRead = notificationIsRead;
	}
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public String getPictureSmallFilePath() {
		return pictureSmallFilePath;
	}
	public void setPictureSmallFilePath(String pictureSmallFilePath) {
		this.pictureSmallFilePath = pictureSmallFilePath;
	}
	public int getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}
	public int getEventsId() {
		return eventsId;
	}
	public void setEventsId(int eventsId) {
		this.eventsId = eventsId;
	}
	public String getNotificationShortContent() {
		return notificationShortContent;
	}
	public void setNotificationShortContent(String notificationShortContent) {
		this.notificationShortContent = notificationShortContent;
	}
	public String getNotificationTitle() {
		return notificationTitle;
	}
	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}
	
}
