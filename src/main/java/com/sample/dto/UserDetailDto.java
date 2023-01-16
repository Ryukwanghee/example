package com.sample.dto;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sample.vo.UserRole;

@Alias("UserDetailDto")
public class UserDetailDto {

	private String id;
	private String name;
	private String email;
	private String tel;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	private List<UserRole> userRoles;
	
	public UserDetailDto() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
}
