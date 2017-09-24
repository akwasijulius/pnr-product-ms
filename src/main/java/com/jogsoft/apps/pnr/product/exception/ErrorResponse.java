package com.jogsoft.apps.pnr.product.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private HttpStatus status;
    private String message;
    private String errors;
    
    public ErrorResponse(){
	}
    
	public ErrorResponse(Exception ex) {
		//this.status = ex.get
		this.message = ex.getMessage();
		this.errors = ex.getStackTrace().toString();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

}
