package com.example.ices.entity;

public class AroundEntity {
	//MSG{"code":0,"message":"��ȡAround�б�ɹ�","token":null,"result":
	//[{"id":1,
	//	"aroundcampusName":"������(������ص�)",
	//	"pictureSmallFilePath":"http://wenwen.soso.com/p/20100204/20100204201123-1524232879.jpg",
	//}
	//"aroundcampusIntroductionShort":"c�ǵķ�˳���������ҷ�˳��ˮ���",
	//"aroundcampusType":1}]}
	private int id;
	private String aroundcampusName;
	private String pictureSmallFilePath;
	private String aroundcampusIntroductionShort;
	private int aroundcampusType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAroundcampusName() {
		return aroundcampusName;
	}
	public void setAroundcampusName(String aroundcampusName) {
		this.aroundcampusName = aroundcampusName;
	}
	public String getPictureSmallFilePath() {
		return pictureSmallFilePath;
	}
	public void setPictureSmallFilePath(String pictureSmallFilePath) {
		this.pictureSmallFilePath = pictureSmallFilePath;
	}
	public String getAroundcampusIntroductionShort() {
		return aroundcampusIntroductionShort;
	}
	public void setAroundcampusIntroductionShort(
			String aroundcampusIntroductionShort) {
		this.aroundcampusIntroductionShort = aroundcampusIntroductionShort;
	}
	public int getAroundcampusType() {
		return aroundcampusType;
	}
	public void setAroundcampusType(int aroundcampusType) {
		this.aroundcampusType = aroundcampusType;
	}
	
}
