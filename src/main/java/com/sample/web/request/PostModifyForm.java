package com.sample.web.request;

import java.util.Date;

public class PostModifyForm {

	private int no;
	private String title;
	private String content; //원래는 여기 세개까지가 필요한데 나머지는 그냥 확인하려고 넣은 것
	private int readCount;
	private String userId;
	private int commentCount;
	private String deleted;
	private Date createdDate;
	private Date updatedDate;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	@Override
	public String toString() {
		return "PostModifyForm [no=" + no + ", title=" + title + ", content=" + content + ", readCount=" + readCount
				+ ", userId=" + userId + ", commentCount=" + commentCount + ", deleted=" + deleted + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + "]";
	}
	
	
	
}
