package com.station3.common;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommonUtils {

	//Map을 Vo로 전환
	public static Object convertMapToObject(Map<String, Object> map, Object obj) {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator<String> itr = map.keySet().iterator();

		while (itr.hasNext()) {
			keyAttribute = itr.next();
			methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methodString.equals(methods[i].getName())) {
					try {
						methods[i].invoke(obj, map.get(keyAttribute));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;
	}

	/*
	* 파일 업로드
	 */
	public static void uploadFile(MultipartFile file, String directoryPath, String fileReName) throws IOException {
		if (!file.isEmpty()) {
			// parent directory를 찾는다.
			Path directory = Paths.get(directoryPath).toAbsolutePath().normalize();

			// directory 해당 경로까지 디렉토리를 모두 만든다.
			Files.createDirectories(directory);

			// 파일명을 바르게 수정한다.
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			if(!ObjectUtils.isEmpty(fileReName)) fileName = fileReName;

			// 파일명에 '..' 문자가 들어 있다면 오류를 발생하고 아니라면 진행(해킹및 오류방지)
			Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");
			// 파일을 저장할 경로를 Path 객체로 받는다.
			Path targetPath = directory.resolve(fileName).normalize();

			// 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
			Assert.state(!Files.exists(targetPath), fileName + " File alerdy exists.");
			file.transferTo(targetPath);
		}
	}

	//파일 존재여부
	public static boolean isExistFile(String filePath) throws IOException {
		final File file = new File(filePath);

		//파일이 존재하는지 확인
		return file.exists();
	}

	/**
	* @content : 클라이언트 IP 가져오기
	* @name :
	* @date : 2021-10-06
	* @author : 신동아
	* @return : String
	**/
	public static String getUserIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * @content : 해당월의 마지막 일 가져오기
	 * @name :
	 * @date : 2021-10-11
	 * @author : 신동아
	 * @return : String
	 **/
	public static int lastDayOfMonth(String yyyyMMdd) {
		YearMonth yearMonth = YearMonth.from(LocalDate.parse(yyyyMMdd, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return yearMonth.lengthOfMonth();
	}

	/**
	 * @content : 현재 실행중인 클래스명
	 * @name :
	 * @date : 2021-10-29
	 * @author : 신동아
	 * @return : String
	 **/
	public static String getClassName(){
		return Thread.currentThread().getStackTrace()[2].getClassName();
	}

	/**
	 * @content : 현재 실행중인 메서드명
	 * @name :
	 * @date : 2021-10-29
	 * @author : 신동아
	 * @return : String
	 **/
	public static String getMethodName(){
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	/**
	 * @content : 현재 실행중인 클래스.메서드명
	 * @name :
	 * @date : 2021-10-29
	 * @author : 신동아
	 * @return : String
	 **/
	public static String getClassAndMethodName(){
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		return className + "." + methodName + "()";
	}

	/**
	 * @content : 문자열 합치기
	 * @name :
	 * @date : 2021-11-28
	 * @author : 신동아
	 * @return : String
	 **/
	public static String stringAppend(String...stringAppend){
		StringBuilder resultString = new StringBuilder();
		for(String string : stringAppend){
			resultString.append(string.toString());
		}

		return resultString.toString();
	}

	/**
	 * @content : 핀스퀘어구독 서비스 업체 로고
	 * @date : 2022-07-08
	 * @author : 박태석
	 * @return : Map<String, String>
	 **/
	public static Map<String, String> pinsquarePartnerLogo(){
		//TODO 나중에 DB로 관리해야 됨
		Map<String, String> svc_logo_list = new HashMap<String, String>();
		svc_logo_list.put("EZWELL","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum1.png"   );
		svc_logo_list.put("PAPER","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum2.png"    );
		svc_logo_list.put("SNACK","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum3.png"    );
		svc_logo_list.put("UBAB","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum4.png"     );
		svc_logo_list.put("UBAB_1","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum4.png"   );
		svc_logo_list.put("UBAB_2","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum4.png"   );
		svc_logo_list.put("UBAB_3","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum4.png"   );
		svc_logo_list.put("UBAB_4","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum4.png"   );
		svc_logo_list.put("PAYCO","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum5.png"    );
		svc_logo_list.put("DRIVE","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum6.png"    );
		svc_logo_list.put("NAMECARD","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/welfare/partner-thum5.png" );
		svc_logo_list.put("YESFORM","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum8.png"  );
		svc_logo_list.put("GOGOX","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/welfare/co-thum9.png"    );
		svc_logo_list.put("GETTY","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum10.png"   );
		svc_logo_list.put("REVU","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum12.png"    );
		svc_logo_list.put("EASY","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/partnerlogo/co-thum13.png"    );
		svc_logo_list.put("SUPERCON","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/welfare/co-thum14.png");
		svc_logo_list.put("BOOKERS","https://castingn-mall.s3.ap-northeast-2.amazonaws.com/static/images/myservice/logo/logo_bookers.png" );
		return svc_logo_list;
	}

	/**
	 * @content : 핀스퀘어구독 서비스 업체명
	 * @date : 2022-07-08
	 * @author : 박태석
	 * @return : Map<String, String>
	 **/
	public static Map<String, String> pinsquarePartnerName(){
		//TODO 나중에 DB로 관리해야 됨
		Map<String, String> svc_name_list = new HashMap<String, String>();
		svc_name_list.put(    "UBAB","유밥");
		svc_name_list.put(    "UBAB_1","유밥");
		svc_name_list.put(    "UBAB_2","유밥");
		svc_name_list.put(    "UBAB_3","유밥");
		svc_name_list.put(    "UBAB_4","유밥");
		svc_name_list.put(    "EZWELL","현대 이지웰");
		svc_name_list.put(    "PAYCO","e-식신");
		svc_name_list.put(    "GOGOX","고고엑스");
		svc_name_list.put(    "PAPER","등기맨");
		svc_name_list.put(    "NAMECARD","업무마켓나인명함");
		svc_name_list.put(    "SNACK","스낵포");
		svc_name_list.put(    "YESFORM","예스폼");
		svc_name_list.put(    "DRIVE","청방");
		svc_name_list.put(    "GETTY","게티이미지뱅크");
		svc_name_list.put(    "REVU","레뷰");
		svc_name_list.put(    "EASY","이지태스크");
		svc_name_list.put(    "SUPERCON","슈퍼콘");
		svc_name_list.put(    "BOOKERS","부커스");
		return svc_name_list;
	}

	/**
	 * @content : 핀스퀘어구독 서비스 카테고리 구분
	 * @date : 2022-07-08
	 * @author : 박태석
	 * @return : Map<String, String>
	 **/
	public static Map<String, String> pinsquareServiceCategory(){
		//TODO 나중에 DB로 관리해야 됨
		Map<String, String> svc_category_list = new HashMap<String, String>();
		svc_category_list.put("UBAB","법정의무교육"        );
		svc_category_list.put("UBAB_1","IT/DT 교육"        );
		svc_category_list.put("UBAB_2","직급교육"          );
		svc_category_list.put("UBAB_3","직무교육"          );
		svc_category_list.put("UBAB_4","산업안전 보건교육" );
		svc_category_list.put("EZWELL","복지몰"            );
		svc_category_list.put("PAYCO","전자식권"           );
		svc_category_list.put("GOGOX","퀵&운송 서비스"      );
		svc_category_list.put("PAPER","등기서비스"         );
		svc_category_list.put("NAMECARD","명함관리&인쇄"   );
		svc_category_list.put("SNACK","간식정기배달"       );
		svc_category_list.put("YESFORM","서식다운로드"     );
		svc_category_list.put("DRIVE","대리운전"           );
		svc_category_list.put("GETTY","이미지다운로드"     );
		svc_category_list.put("REVU","마케팅 체험단"       );
		svc_category_list.put("EASY","사무알바"            );
		svc_category_list.put("SUPERCON","모바일상품권"    );
		svc_category_list.put("BOOKERS","전자도서"    );
		return svc_category_list;
	}
}
