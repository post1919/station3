package com.station3.common;

import org.springframework.stereotype.Component;

@Component
public class ConstantProperties {

	/*
	* 신규 업무마켓 리뉴얼시 부터 생성하는 변수들
	 */
	public static final String PATH_MAIN = "/main";

	/*
	* LHK
	 */
	public static final String SITE_URL = "https://biz.workmarket9.com";

	//리소스 (이미지, css, js) 도메인
	public static final String PATH_DOMAIN = "https://resource.mall.castingn.com";
	public static final String PATH_STATIC = "/static"; // resource static
	public static final String PATH_IMG = PATH_DOMAIN + PATH_STATIC + "/images"; // resource img
	public static final String PATH_BI = PATH_IMG + "/sub/bi";
	public static final String PATH_JS  = PATH_STATIC + "/js"; // resource js
	public static final String PATH_CSS = PATH_STATIC + "/css"; // resource css

	// 리턴 메세지 plain text
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

	public static String PATH_UPLOAD 			= "/var/www/castingn/upload/";
	public static String PATH_PROJECT_FILE 		= "/var/www/castingn/files/project/"; // 프로젝트 관련 파일 저장
	public static String PATH_CASTINGN_PROJECT_FILE = "/var/www/castingn/files/project/"; // 프로젝트 관련 파일 저장

	public static String PATH_PROFILE_PICTURE 	= "/var/www/castingn/images/profile/"; // 고객 사진
	public static String PATH_USER_COMPANY_PICTURE 	= "/var/www/castingn/images/profile/userCompany/"; // 고객 회사 사진

	public static String PATH_CASTING = "/home/casting/casting/data/"; // php서버 파일 경로
	public static String PATH_CASTING_VIEW = "/casting/"; // php서버 파일 VIEW경로

	public static String PATH_PRODUCT_COMPANY_LOGO = PATH_CASTING + "casting/Bizimg/"; // 상품 파트너 로고
	public static String PATH_PRODUCT_COMPANY_LOGO_VIEW = PATH_CASTING_VIEW + "casting/Bizimg/"; // 상품 파트너 로고 > VIEW 경로

	public static String PATH_PRODUCT_IMAGE = "/home/casting/casting/data/casting/Product_images/"; // 상품 이미지

	public static String PATH_UPLOAD_BUYR 		= "/home/casting/casting/Buyr/files/";
	public static String PATH_UPLOAD_REQ 		= "/home/casting/casting/data/reqFile/";
	public static String PATH_UPLOAD_LICENSE	= "/home/casting/casting/data/casting/license/";
	public static String PATH_VIEW_REQ 			= "/casting/data/reqFile/";
	public static String SERVER_NAME = "https://www.castingn.com";

	public static String URL_IMAGE 				= "/image";
	public static String URL_PROFILE 			= URL_IMAGE+"/profile/";
}
