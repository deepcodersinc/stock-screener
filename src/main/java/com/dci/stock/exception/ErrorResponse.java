/**
 * Error bean response for client
 * 
 * @author Soumyadeep Mahapatra
 * 
 */
package com.dci.stock.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private String message;
	private int httpCode;
	 
	public ErrorResponse(String message, HttpStatus httpStatus){
		this.message = message;
		this.httpCode = httpStatus.value();
	}

	public int getHttpCode() {
		return httpCode;
	}
	
	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public String getMessage() {
		return message;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
}