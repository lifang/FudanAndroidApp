package com.example.ices.entity;

import java.util.List;

public class EventDetailEneity {
	private List<PicturesEntity> pictures;
//    "id": 2,
//    "createTime": 1418171400000,
//    "createUser": null,
//    "eventsAddress": "½Ì1-304",
//    "eventsCostMoney": 0,
//    "eventsFinshTime": 1418486400000,
//    "eventsIntroduction": "Ð´¸Ö±Ê×Ö,Ã«±Ê×Ö,Ð´¸Ö±Ê×Ö,Ã«±Ê×Ö",
//    "eventsIntroductionShort": "Ð´¸Ö±Ê×Ö,Ã«±Ê×Ö...",
//    "eventsName": "±³Êé±ÈÈü",
//    "eventsPhone": "15673678833",
//    "eventsStartTime": 1418313600000,
//    "eventsStatus": 2,
//    "eventsType": 1,
	private int eventsIsJoin;
	public int getEventsIsJoin() {
		return eventsIsJoin;
	}
	public void setEventsIsJoin(int eventsIsJoin) {
		this.eventsIsJoin = eventsIsJoin;
	}
	private int id;
	private String  createTime;
	private String createUser;
	private String eventsAddress;
	private float eventsCostMoney;
	private String eventsFinshTime;
	private String eventsIntroduction;
	private String eventsIntroductionShort;
	private String eventsName;
	private String eventsPhone;
	private String eventsStartTime;
	private int eventsStatus;
	private int eventsType;
	public List<PicturesEntity> getPictures() {
		return pictures;
	}
	public void setPictures(List<PicturesEntity> pictures) {
		this.pictures = pictures;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getEventsAddress() {
		return eventsAddress;
	}
	public void setEventsAddress(String eventsAddress) {
		this.eventsAddress = eventsAddress;
	}
	public float getEventsCostMoney() {
		return eventsCostMoney;
	}
	public void setEventsCostMoney(float eventsCostMoney) {
		this.eventsCostMoney = eventsCostMoney;
	}
	public String getEventsFinshTime() {
		return eventsFinshTime;
	}
	public void setEventsFinshTime(String eventsFinshTime) {
		this.eventsFinshTime = eventsFinshTime;
	}
	public String getEventsIntroduction() {
		return eventsIntroduction;
	}
	public void setEventsIntroduction(String eventsIntroduction) {
		this.eventsIntroduction = eventsIntroduction;
	}
	public String getEventsIntroductionShort() {
		return eventsIntroductionShort;
	}
	public void setEventsIntroductionShort(String eventsIntroductionShort) {
		this.eventsIntroductionShort = eventsIntroductionShort;
	}
	public String getEventsName() {
		return eventsName;
	}
	public void setEventsName(String eventsName) {
		this.eventsName = eventsName;
	}
	public String getEventsPhone() {
		return eventsPhone;
	}
	public void setEventsPhone(String eventsPhone) {
		this.eventsPhone = eventsPhone;
	}
	public String getEventsStartTime() {
		return eventsStartTime;
	}
	public void setEventsStartTime(String eventsStartTime) {
		this.eventsStartTime = eventsStartTime;
	}
	public int getEventsStatus() {
		return eventsStatus;
	}
	public void setEventsStatus(int eventsStatus) {
		this.eventsStatus = eventsStatus;
	}
	public int getEventsType() {
		return eventsType;
	}
	public void setEventsType(int eventsType) {
		this.eventsType = eventsType;
	}
	
	
	
	
	
	
	
	
	
} 
