package com.example.ices.entity;

import java.util.List;

public class MenuTitle {
	private String directoryName;
	private int directorId;
	private List<ContentList> contentList;
	public String getDirectoryName() {
		return directoryName;
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	public int getDirectorId() {
		return directorId;
	}
	public void setDirectorId(int directorId) {
		this.directorId = directorId;
	}
	public List<ContentList> getContentList() {
		return contentList;
	}
	public void setContentList(List<ContentList> contentList) {
		this.contentList = contentList;
	}
	
}
