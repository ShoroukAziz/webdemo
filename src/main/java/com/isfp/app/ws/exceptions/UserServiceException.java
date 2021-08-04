package com.isfp.app.ws.exceptions;

public class UserServiceException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5720175245666353215L;

	public UserServiceException (String message) {
		super(message);
	}

}
