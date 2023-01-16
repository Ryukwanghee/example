package com.sample.dto;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.sample.vo.AttachedFile;
import com.sample.vo.Tag;

@Alias("PostDetailDto")
public class PostDetailDto {	//출력화면 표현하는 DTO 정의하고 DTO에 필요한 정보 전부 조회해서 저장

	//게시글정보
	private String title;
	private int no;
	private String userId;
	private String userName;
	private int readCount;
	private int commentCount;
	private String content;
	private Date createdDate;
	private Date updatedDate;			// title부터 여기까지는 spring_users, spring_posts -> PostDetailDto postmapper.getPostDetail(postNo)
	//댓글정보
	private List<PostCommentListDto> comments;	//spring_users와 spring_post_comments -> List<PostCommentListDto> getPostCommentsByPostNo(postNo)
	// 첨부파일정보
	private List<AttachedFile> attachedFiles;	//spring_post_attached_files -> List<AttachedFile> getAttachedFileByPostNo(postNo)
	// 태그정보
	private List<Tag> tags;	// spring_post_tags 테이블 -> List<Tag> getTagsByPostNo(postNo)
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public List<PostCommentListDto> getComments() {
		return comments;
	}
	public void setComments(List<PostCommentListDto> comments) {
		this.comments = comments;
	}
	public List<AttachedFile> getAttachedFiles() {
		return attachedFiles;
	}
	public void setAttachedFiles(List<AttachedFile> attachedFiles) {
		this.attachedFiles = attachedFiles;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	
}
