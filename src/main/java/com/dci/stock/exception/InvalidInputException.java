package com.dci.stock.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
		
	public InvalidInputException(String message) {
		super(message);
	}	
	
	public InvalidInputException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}