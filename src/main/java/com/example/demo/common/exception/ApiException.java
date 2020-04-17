package com.example.demo.common.exception;

import com.example.demo.common.enums.WebErrCode;

public class ApiException extends Exception {
	
	private static final long serialVersionUID = 1L;
	public int code;
	
	public ApiException(WebErrCode webErrCode) {
		super(webErrCode.getMessage());
		this.setCode(webErrCode.getCode());
	}
	
	public ApiException(String message , int code) {
		super(message);
		this.setCode(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}