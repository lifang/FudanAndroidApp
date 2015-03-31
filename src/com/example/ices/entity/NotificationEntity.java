package com.example.ices.entity;

public class NotificationEntity {
//	“notificationTitle” : “xxxx”,
//	“notificationId” : “xxxx”,
//	“notificationType” : “xxxx”,
//	“notificationIsRead” : “xxxx”,
//	“notificationContent” : “xxxx”, //截断100长度或其它
//	“notificationPictureFilePath” : “xxxx” //小图
	private String notificationTitle;
	private String notificationId;
	private String notificationType;
	private String notificationIsRead;
	private String notificationContent;
	private String notificationPictureFilePath;
	public String getNotificationTitle() {
		return notificationTitle;
	}
	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getNotificationIsRead() {
		return notificationIsRead;
	}
	public void setNotificationIsRead(String notificationIsRead) {
		this.notificationIsRead = notificationIsRead;
	}
	public String getNotificationContent() {
		return notificationContent;
	}
	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}
	public String getNotificationPictureFilePath() {
		return notificationPictureFilePath;
	}
	public void setNotificationPictureFilePath(String notificationPictureFilePath) {
		this.notificationPictureFilePath = notificationPictureFilePath;
	}
	
}
