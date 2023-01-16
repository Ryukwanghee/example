package com.sample.vo;

import org.apache.ibatis.type.Alias;

@Alias("Tag")
public class Tag {
	
	private int postNo;
	private String content;
	
	public Tag() {}

	public Tag(int postNo, String content) {
		this.postNo = postNo;
		this.content = content;
	}
	
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
