package com.sample.vo;

import org.apache.ibatis.type.Alias;

@Alias("AttachedFile")
public class AttachedFile {

	private int postNo;
	private String filename;
	
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
