package com.example.ices.entity;

import java.util.List;

public class AroundDetailEntity {
	//:{"id":1,"aroundcampusAddress":"苏州金阊区广济南路219号新苏天地购物中心7楼(近金阊医院)",
	//"aroundcampusIntroduction":"afsdfsdfsfsdfsdf",
	//"aroundcampusIntroductionShort":"c是的发顺丰撒发而我发顺丰水电费",
	//"aroundcampusLatitude":"120.606557","aroundcampusLongitude":"31.315927",
	//"aroundcampusName":"海底捞(新苏天地店)","aroundcampusPhone":"13212345678",
	//"aroundcampusStartHour":null,"aroundcampusStartMinute":null,
	//"aroundcampusFinshHour":null,"aroundcampusFinshMinute":null,"aroundcampusStatus":2,"aroundcampusType":1,
	//"pictures":[{"id":8,"createTime":null,"createUser":null,"foreignId":1,"foreignType":3,"pictureLargeFilePath":"http://wenwen.soso.com/p/20100204/20100204201123-1524232879.jpg","pictureSmallFilePath":"http://wenwen.soso.com/p/20100204/20100204201123-1524232879.jpg","updateTime":null,"updateUser":null,"version":0}],
	//"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"version":0}}
	private int id;
	private String aroundcampusAddress;
	private String aroundcampusIntroduction;
	private String aroundcampusIntroductionShort;
	private String aroundcampusLatitude;
	private String aroundcampusLongitude;
	private String aroundcampusName;
	private String aroundcampusPhone;
	private String aroundcampusStartHour;
	private String aroundcampusStartMinute;
	private String aroundcampusFinshHour;
	private String aroundcampusFinshMinute;
	private int aroundcampusStatus;
	private int aroundcampusType;
	private List<PicturesEntity>pictures;
	
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
	public String getAroundcampusAddress() {
		return aroundcampusAddress;
	}
	public void setAroundcampusAddress(String aroundcampusAddress) {
		this.aroundcampusAddress = aroundcampusAddress;
	}
	public String getAroundcampusIntroduction() {
		return aroundcampusIntroduction;
	}
	public void setAroundcampusIntroduction(String aroundcampusIntroduction) {
		this.aroundcampusIntroduction = aroundcampusIntroduction;
	}
	public String getAroundcampusIntroductionShort() {
		return aroundcampusIntroductionShort;
	}
	public void setAroundcampusIntroductionShort(
			String aroundcampusIntroductionShort) {
		this.aroundcampusIntroductionShort = aroundcampusIntroductionShort;
	}
	public String getAroundcampusLatitude() {
		return aroundcampusLatitude;
	}
	public void setAroundcampusLatitude(String aroundcampusLatitude) {
		this.aroundcampusLatitude = aroundcampusLatitude;
	}
	public String getAroundcampusLongitude() {
		return aroundcampusLongitude;
	}
	public void setAroundcampusLongitude(String aroundcampusLongitude) {
		this.aroundcampusLongitude = aroundcampusLongitude;
	}
	public String getAroundcampusName() {
		return aroundcampusName;
	}
	public void setAroundcampusName(String aroundcampusName) {
		this.aroundcampusName = aroundcampusName;
	}
	public String getAroundcampusPhone() {
		return aroundcampusPhone;
	}
	public void setAroundcampusPhone(String aroundcampusPhone) {
		this.aroundcampusPhone = aroundcampusPhone;
	}
	public String getAroundcampusStartHour() {
		return aroundcampusStartHour;
	}
	public void setAroundcampusStartHour(String aroundcampusStartHour) {
		this.aroundcampusStartHour = aroundcampusStartHour;
	}
	public String getAroundcampusStartMinute() {
		return aroundcampusStartMinute;
	}
	public void setAroundcampusStartMinute(String aroundcampusStartMinute) {
		this.aroundcampusStartMinute = aroundcampusStartMinute;
	}
	public String getAroundcampusFinshHour() {
		return aroundcampusFinshHour;
	}
	public void setAroundcampusFinshHour(String aroundcampusFinshHour) {
		this.aroundcampusFinshHour = aroundcampusFinshHour;
	}
	public String getAroundcampusFinshMinute() {
		return aroundcampusFinshMinute;
	}
	public void setAroundcampusFinshMinute(String aroundcampusFinshMinute) {
		this.aroundcampusFinshMinute = aroundcampusFinshMinute;
	}
	public int getAroundcampusStatus() {
		return aroundcampusStatus;
	}
	public void setAroundcampusStatus(int aroundcampusStatus) {
		this.aroundcampusStatus = aroundcampusStatus;
	}
	public int getAroundcampusType() {
		return aroundcampusType;
	}
	public void setAroundcampusType(int aroundcampusType) {
		this.aroundcampusType = aroundcampusType;
	}
	
}
