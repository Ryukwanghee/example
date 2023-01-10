package com.sample.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sample.exception.ApplicationException;
import com.sample.utils.SessionUtils;
import com.sample.web.login.LoginUser;
import com.sample.web.login.LoginUserInfo;

/**
 * HandlerMethodArgumentResolver는 요청핸들러의 매개변수를 분석해서 적절한 값을 매개변수에 전달한다.
 * @author kwang
 *
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * 요청핸들러 메소드의 매개변수가 @LoginUser 어노테이션을 가지고 있으면 true를 반환한다.
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 요청핸들러 메소드의 매개변수가 @LoginUser 어노테이션을 가지고 있으면 true를 반환한다.
		return parameter.hasParameterAnnotation(LoginUser.class); //파라미터가 로그인유저 어노테이션을 가지고 있냐 갖고있으면 true 없으면 false반환
		// **UserController의 info 파라미터에 loginUserinfo에 반환되는 true or false를 전달. 로그인 안돼있으면 Model은 false*****
	}
	
	/**
	 * @LoginUser 어노테이션을 가지고 있는 요청핸들러 메소드의 매개변수에 제공할 값을 반환한다.
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, 
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		// 로그인한 사용자가 있으면 true반환, model에 저장된값이 없으면 false 반환
		
		// **UserController의 info 파라미터에 loginUserinfo에 반환되는 true or false를 전달. 로그인 안돼있으면 Model은 false*****
		
		// 세션에 "loginUser"라는 속성명으로 저장된 객체를 조회한다.
		LoginUserInfo loginUserInfo = (LoginUserInfo) SessionUtils.getAttribute("loginUser");	// 로그인된 사용자 정보가 UserController의 LoginUserInfo로 전달
		
		// 매개변수의 어노테이션중에서 @LoginUser 어노테이션을 조회한다.
		LoginUser loginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class);
		// @LoginUser 어노테이션의 required 속성값을 조회한다.
		boolean required = loginUserAnnotation.required();
		// required가 true고 loginUserInfo가 null이면 예외를 던진다.
		if (required && loginUserInfo == null) {
			throw new ApplicationException("로그인이 필요한 서비스입니다.");
		}
		
		
		return loginUserInfo;
	}
}
