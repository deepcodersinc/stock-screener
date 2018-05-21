package com.dci.stock.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {
	
	@ExceptionHandler(InvalidInputException.class)
	public ErrorResponse handleNotFoundException(InvalidInputException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getHttpStatus());
		return error;
	}
}
