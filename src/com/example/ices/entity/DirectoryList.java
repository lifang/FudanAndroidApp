package com.example.ices.entity;

import java.util.List;
/***
 * 
*    
* �����ƣ�DirectoryList   
* ��������   pre arrival ʵ����
* �����ˣ� ljp 
* ����ʱ�䣺2014-12-11 ����2:26:20   
* @version    
*
 */
public class DirectoryList {
	private List<ContentListEntity> contentList;
	private String directoryId;
	private String directoryTitle;
	public List<ContentListEntity> getContentList() {
		return contentList;
	}
	
	public void setContentList(List<ContentListEntity> contentList) {
		this.contentList = contentList;
	}
	public String getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}
	public String getDirectoryTitle() {
		return directoryTitle;
	}
	public void setDirectoryTitle(String directoryTitle) {
		this.directoryTitle = directoryTitle;
	}

	public DirectoryList(List<ContentListEntity> contentList,
			String directoryId, String directoryTitle) {
		super();
		this.contentList = contentList;
		this.directoryId = directoryId;
		this.directoryTitle = directoryTitle;
	}
	
}
