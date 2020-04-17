package com.example.demo.common.enums;

public enum WebErrCode {
	err200(200,"success")
	,err400(400,"data not found")
	,err403(403,"權限不足")
	,err404(404,"bad request")
	,err500(500,"後端程式錯誤，無法給資料");
	
	WebErrCode(int code,String message){
		this.code = code;
		this.message = message;
	}
	
	int code;
	String message;
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
}
