package com.isfp.app.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.isfp.app.ws.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler {
	
		@ExceptionHandler(value = {UserServiceException.class})
		public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest req){	
			
			ErrorMessage ErrorMessage = new ErrorMessage(new Date(), ex.getMessage());
			
			return new ResponseEntity<>(ErrorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}


	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest req){	
		
		ErrorMessage ErrorMessage = new ErrorMessage(new Date(), ex.getMessage());
		
		return new ResponseEntity<>(ErrorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
