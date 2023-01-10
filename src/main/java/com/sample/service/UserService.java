package com.sample.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dto.UserDetailDto;
import com.sample.exception.ApplicationException;
import com.sample.mapper.UserMapper;
import com.sample.mapper.UserRoleMapper;
import com.sample.vo.User;
import com.sample.vo.UserRole;
import com.sample.web.request.UserRegisterForm;

@Service // 서비스에서 컨트롤러로 의존성 주입(UserService -> HomeController)
public class UserService {
	
	
	@Autowired //자동 의존성주입
	private UserMapper userMapper;
	@Autowired //자동 의존성주입
	private UserRoleMapper userRoleMapper;

	public void registerUser(UserRegisterForm userRegisterForm) {
		User savedUser = userMapper.getUserById(userRegisterForm.getId()); //유저정보를 조회
		if (savedUser != null) {
			throw new ApplicationException("["+userRegisterForm.getId()+"] 사용할 수 없는 아이디입니다.");
		}
		savedUser = userMapper.getUserByEmail(userRegisterForm.getEmail());
		if (savedUser != null) {
			throw new ApplicationException("["+userRegisterForm.getEmail()+"] 사용할 수 없는 이메일입니다.");
		}
		
		User user = new User();
		BeanUtils.copyProperties(userRegisterForm, user); // userRegisterForm과 user의 변수명이 같은것만 값을 복사함, 편리하게 이용하려고
		/*
		 * user.setId(userRegisterForm.getId());
		 * user.setPassword(userRegisterForm.getPassword());
		 * user.setName(userRegisterForm.getName());
		 * user.setEmail(userRegisterForm.getEmail());
		 * user.setTel(userRegisterForm.getTel());
		 */
		
		userMapper.insertUser(user);
		
		UserRole userRole = new UserRole(userRegisterForm.getId(), userRegisterForm.getRoleName());
		userRoleMapper.insertUserRole(userRole);
	}
	
	public User login(String userId, String password) {
		User savedUser = userMapper.getUserById(userId);
		if (savedUser == null) {
			throw new ApplicationException("아이디 혹은 비밀번호가 올바르지 않습니다.");
		}
		if ("Y".equals(savedUser.getDeleted())) {
			throw new ApplicationException("탈퇴처리된 사용자 계정으로 로그인할 수 없습니다.");
		}
		if (!savedUser.getPassword().equals(password)) {
			throw new ApplicationException("아이디 혹은 비밀번호가 올바르지 않습니다.");
		}
		return savedUser;
	}
	
	public UserDetailDto getUserDetail(String userId) { // 이 정보를 컨트롤러에서 호출한다-> UserController
		// 사용자 정보 조회
		User user = userMapper.getUserById(userId);
		// 사용자의 권한 정보 조회
		List<UserRole> userRoles = userRoleMapper.getUserRolesByUserId(userId);
		
		// 사용자 정보와 권한정보를 모두 저장하는 UserDetailDto 객체 생성
		UserDetailDto userDetailDto = new UserDetailDto();
		// User객체의 값을 UserDetailDto객체로 복사하기
		BeanUtils.copyProperties(user, userDetailDto);
		// 사용자권한정보를 UserDetailDto객체에 저장하기
		userDetailDto.setUserRoles(userRoles);
		
		return userDetailDto;
	}
	
	public void changePassword(String userId, String oldPassword, String password) {
		User user = userMapper.getUserById(userId);
		if (user == null) {
			throw new ApplicationException("사용자 정보가 존재하지 않아서 비밀번호를 변경할 수 없습니다.");
		}
		if ("Y".equals(user.getDeleted())) {
			throw new ApplicationException("이미 탈퇴처리된 사용자는 비밀번호를 변경할 수 없습니다.");
		}
		if (!user.getPassword().equals(password)) {
			throw new ApplicationException("비밀번호가 일치하지 않아서 비밀번호를 변경할 수 없습니다.");
		}
		
		user.setPassword(password);
		userMapper.updateUser(user);
	}
	
	public void deleteUser(String userId, String password) {
		User user = userMapper.getUserById(userId);
		if (user == null) {
			throw new ApplicationException("사용자 정보가 존재하지 않아서 탈퇴처리 할 수 없습니다.");
		}
		if ("Y".equals(user.getDeleted())) {
			throw new ApplicationException("이미 탈퇴처리된 사용자입니다.");
		}
		if (!user.getPassword().equals(password)) {
			throw new ApplicationException("비밀번호가 일치하지 않아서 탈퇴처리할 수 없습니다.");
		}
		user.setDeleted("Y");
		userMapper.updateUser(user);
	}
	
	public void addUserRole(UserRole userRole) {
		
	}
}
