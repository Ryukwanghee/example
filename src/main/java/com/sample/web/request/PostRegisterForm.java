package com.sample.web.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class PostRegisterForm {

	private String title;
	private String content;
	private MultipartFile upfile;	// <input type="file" name="upfile"> 이렇게 되어있어서 이 이름과 같게 만드는 것, 첨부파일도 여러개면 List<MultiparFile> 하면됨
	private List<String> tags;		// <input type="hidden" name="tags" value="스프링"> <input type="hidden" name="tags" value="자바"> 처럼 여러개
	
	private String filename;		// <form> 태그에는 없는 필드임. 첨부파일 이름을 저장하기 위해서 생성한 변수
	
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
	public MultipartFile getUpfile() {
		return upfile;
	}
	public void setUpfile(MultipartFile upfile) {
		this.upfile = upfile;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
