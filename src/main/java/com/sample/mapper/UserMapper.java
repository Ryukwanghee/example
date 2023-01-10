package com.sample.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sample.vo.User;

@Mapper
public interface UserMapper {

	void insertUser(User user);
	void updateUser(User user);
	User getUserById(String userId);
	User getUserByEmail(String email);
}
