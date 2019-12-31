package com.sjs.exception;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = 6833810947918945230L;
 
	public UserServiceException(String message){
		super(message);
	}
}
