package com.example.demo.common.util;

import com.example.demo.common.enums.WebErrCode;
import com.example.demo.common.exception.ApiException;

import java.util.HashMap;
import java.util.Map;


public class ResultHelper {

	public static Map<String, Object> returnResult(WebErrCode webErrCode, Object items) {

		Map<String, Object> map = new HashMap();
		map.put("code", webErrCode.getCode());
		map.put("message", webErrCode.getMessage());
		if (webErrCode.getCode() == 200) {
			Map<String,Object> data = new HashMap<>();
			data.put("items", items);
			map.put("data", data);
		}
		return map;
	}

	public static Map<String, Object> returnResult(WebErrCode webErrCode, Map<String, Object> data) {
		Map<String, Object> map = new HashMap();
		map.put("code", webErrCode.getCode());
		map.put("message", webErrCode.getMessage());
		if (webErrCode.getCode() == 200) {
			map.put("data", data);
		}
		return map;
	}
	
	public static Map<String, Object> returnException(ApiException e){

		Map<String, Object> result = new HashMap<>();
		result.put("code", e.getCode());
		result.put("message", e.getMessage());

		e.printStackTrace();
		
		return result;
	}

	public static Map<String, Object> returnException(WebErrCode webErrCode, String message){

		Map<String, Object> result = new HashMap<>();
		result.put("code", webErrCode.getCode());
		result.put("message", message);

		return result;
	}

}
