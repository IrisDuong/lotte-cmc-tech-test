package com.cmc.util.exception;

import com.cmc.util.enums.HttpErrorCode;

public class BaseException extends RuntimeException{

	private final HttpErrorCode httpErrorCode;

	public  BaseException(HttpErrorCode httpErrorCode,String message) {
		super(message);
		this.httpErrorCode = httpErrorCode;
	}

	public  BaseException(HttpErrorCode httpErrorCode) {
		super(httpErrorCode.getMessage());
		this.httpErrorCode = httpErrorCode;
	}

	public HttpErrorCode getHttpErrorCode() {
		return httpErrorCode;
	}
	
}
