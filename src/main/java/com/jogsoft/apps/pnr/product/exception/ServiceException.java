package com.jogsoft.apps.pnr.product.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpStatus;
	
	public ServiceException(String message) {
		super(message);
		this.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		this.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ServiceException(Throwable cause, HttpStatus httpStatus) {
		super(cause);
		this.setHttpStatus(httpStatus);
	}


	public ServiceException(String message, Throwable cause, HttpStatus httpStatus) {
		super(message, cause);
		this.setHttpStatus(httpStatus);
	}


	public HttpStatus getHttpStatus() {
		return httpStatus;
	}


	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	

}
