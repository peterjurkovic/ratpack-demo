package com.peterjurkovic.upstream.handler;

public class BaseResponse {

	private final boolean error;
	private final String message;

	public BaseResponse(boolean error, String message) {
		this.error = error;
		this.message = message;
	}

	public static BaseResponse error(String message) {
		return new BaseResponse(true, message);
	}

	public boolean isError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
