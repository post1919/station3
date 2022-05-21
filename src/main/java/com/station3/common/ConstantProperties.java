package com.station3.common;

import org.springframework.stereotype.Component;

@Component
public class ConstantProperties {

	//리소스 (image, css, js)
	public static final String PATH_STATIC = "/static"; // resource static
	public static final String PATH_IMG = PATH_STATIC + "/images"; // resource img
	public static final String PATH_JS  = PATH_STATIC + "/js"; // resource js
	public static final String PATH_CSS = PATH_STATIC + "/css"; // resource css

	// Response
	public static String OUTPUT_OK = "OK";
	public static String OUTPUT_SUCCESS = "SUCCESS";
	public static String OUTPUT_FAIL = "FAIL";
	public static String OUTPUT_EXIST = "EXIST";
	public static String OUTPUT_ERROR = "ERROR";
	public static String OUTPUT_NEED_LOGIN = "NEED_LOGIN";

	public static String RESPONSE_STATUS_200 = "OK";
	public static String RESPONSE_STATUS_400 = "Bad Request";
	public static String RESPONSE_STATUS_401 = "Unauthorized";
	public static String RESPONSE_STATUS_403 = "Forbidden";
	public static String RESPONSE_STATUS_500 = "Internal Error";
}
