/**
 * 
 */
package com.jogsoft.apps.pnr.product.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jogsoft.apps.pnr.product.exception.ErrorResponse;
import com.jogsoft.apps.pnr.product.exception.ServiceException;

/**
 * @author Julius Oduro
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	Logger logger = Logger.getLogger("RestExceptionHandler");

	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleRestException(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex);
		logger.log(Level.SEVERE, ex.getMessage(), ex);
		if (ex instanceof ServiceException) {
			errorResponse.setStatus(((ServiceException) ex).getHttpStatus());
			return new ResponseEntity<Object>(errorResponse, ((ServiceException) ex).getHttpStatus());
		} else {
			return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		// logger.log(Level.SEVERE, ex.getMessage(), ex);
		
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		
		List<ErrorResponse> errors = new ArrayList<>(violations.size());
		for (ConstraintViolation<?> violation : violations) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage(violation.getMessage());
			errorResponse.setErrors(violation.getPropertyPath().toString());
			errorResponse.setStatus(HttpStatus.BAD_REQUEST);
			errors.add(errorResponse);
		}
		
		
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

}
