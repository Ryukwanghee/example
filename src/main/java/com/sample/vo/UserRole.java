package com.sample.vo;

import org.apache.ibatis.type.Alias;

@Alias("UserRole")
public class UserRole {

	private String userId;
	private String roleName;
	
	public UserRole() {}
	public UserRole(String userId, String roleName) {
		this.userId = userId;
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
