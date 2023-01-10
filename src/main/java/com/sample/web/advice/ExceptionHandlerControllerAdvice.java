package com.sample.web.advice;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sample.exception.ApplicationException;

@ControllerAdvice	//모든 컨트롤러에서 공통으로 사용하는 기능 , 컨트롤러 어드바이스로 일괄적인 예외처리;
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ApplicationException.class)
	public String handleApplicationException(ApplicationException ex) {
		ex.printStackTrace();
		return "error/500";
	}
	
	@ExceptionHandler(DataAccessException.class)
	public String handleApplicationException(DataAccessException ex) {
		ex.printStackTrace();
		return "error/500";
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String handleApplicationException(RuntimeException ex) {
		ex.printStackTrace();
		return "error/500";
	}
}
