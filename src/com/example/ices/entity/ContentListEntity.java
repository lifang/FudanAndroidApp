package com.example.ices.entity;

public class ContentListEntity {
	private String directory_contentContentTitle;
	private String directory_contentId;
	public String getDirectory_contentContentTitle() {
		return directory_contentContentTitle;
	}
	public void setDirectory_contentContentTitle(
			String directory_contentContentTitle) {
		this.directory_contentContentTitle = directory_contentContentTitle;
	}
	public String getDirectory_contentId() {
		return directory_contentId;
	}
	public void setDirectory_contentId(String directory_contentId) {
		this.directory_contentId = directory_contentId;
	}
	public ContentListEntity(String directory_contentContentTitle,
			String directory_contentId) {
		super();
		this.directory_contentContentTitle = directory_contentContentTitle;
		this.directory_contentId = directory_contentId;
	}
	
}
