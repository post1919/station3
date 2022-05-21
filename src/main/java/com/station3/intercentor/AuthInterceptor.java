package com.station3.intercentor;

import com.station3.common.ConstantProperties;
import com.station3.login.dto.CustomUserDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class AuthInterceptor implements HandlerInterceptor {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				String key = cookies[i].getName();
				ResponseCookie cookie = ResponseCookie.from(key, cookies[i].getValue())
						//.domain(iniSiteDomain)
						.sameSite("None")
						.secure(true)
						.path("/")
						.build();
				response.addHeader("Set-Cookie", cookie.toString());

			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {

		if( mav != null ) {
			HttpSession session = request.getSession();

			String uri = request.getRequestURI();

			//로그인 후 현재페이지로 이동[s]
			String query = request.getQueryString();

			//로그인 queryString 처리 & , amp; 가 중복으로 발생되는 경우 있어 방지
			if( query != null ){
				query = query.replace("amp;", "");
			}

			String currentURL = request.getRequestURI();
			if(query!=null && query.length()>0) {
				currentURL += "?"+query;
			}

			//로그인상태일때 특정 URL 접근 불가 => 스프링시큐리티로 설정하도록 변경해야함
			/*if( Objects.nonNull(session.getAttribute("USER")) && (currentURL.startsWith("/login") || currentURL.startsWith("/join")) )
			{
				response.sendRedirect("/");
			}*/

			mav.addObject("RETURN_URL", URLEncoder.encode(currentURL, "UTF-8"));
			//로그인 후 현재페이지로 이동[e]

			mav.addObject("PATH_IMG", ConstantProperties.PATH_IMG);
			mav.addObject("PATH_JS", ConstantProperties.PATH_JS);
			mav.addObject("PATH_CSS", ConstantProperties.PATH_CSS);
			mav.addObject("PATH_LIB", ConstantProperties.PATH_STATIC);

			mav.addObject("DATE_yyyyMMddHHmmss", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

			//최상위메뉴(홈/로그인시/카테고리 메뉴펼침) or 기타
			String headerType = "1";

			//최상위메뉴 > 메뉴
			if( uri.equals("/mypage/main") || uri.equals("/convenience/pinSquare") || uri.equals("/product/productMain") || uri.equals("/event/eventList")
					|| uri.equals("/product/serviceCategory")
			){
				headerType = "2";

				//하위 depth
			} else if( uri.indexOf("/mypage/") > -1 || uri.indexOf("/sourcing/") > -1 || uri.indexOf("/order/") > -1 ||
					uri.equals("/claim/claimList")
			){
				headerType = "3";

				//오피스스토어 하위
			} else if( uri.indexOf("/product/") > -1 ){
				headerType = "product";

				//핀스퀘어 하위
			} else if( uri.indexOf("/convenience/") > -1 ){
				headerType = "pinsquare";

			/*//취하위 depth
			} else if( ){
				headerType = 4;*/
			}

			mav.addObject("headerType", headerType);

			CustomUserDetailsDto user = null;
			String authType = null; //관리자일경우 타입
			if( Objects.nonNull(session.getAttribute("USER")) ) {
				user = (CustomUserDetailsDto) session.getAttribute("USER");
				authType = user.getAuthType();
			}

			//화면별 권한 체크[s]
			/*if( Objects.nonNull(authType) && uri.equals("/mypage/userCompanyManagerList") ){
				boolean screenAuth = true;

				//전체관리자 제외
				if( !Objects.equals(authType, "TOTAL_MANAGER") ){
					List<UserCompanyManagerAuthFunctionResponseDto> authFunctionList = user.getAuthFunctionList();
					Long authCheckCount = authFunctionList.stream()
							.filter(auth->UserCompanyManagerAuthFunctionType.COMP_INFO == UserCompanyManagerAuthFunctionType.getByCode(auth.getAuthFunction())).count();
					if (authCheckCount == 0L) {
						screenAuth = false;
					}
				}

				if (!screenAuth) {
					response.sendRedirect("/");
				}
			}*/
			//화면별 권한 체크[e]

			//마이페이지-나의계정 => SNS연동 할때 기존 연동된 정보 있는경우 처리위해
			if( Objects.nonNull(session.getAttribute("USER_SNS_PROGRESS")) ){
				String userSnsProgress = String.valueOf(session.getAttribute("USER_SNS_PROGRESS"));
				if( "EXIST".equals(userSnsProgress) ){
					mav.addObject("USER_SNS_PROGRESS_EXIST", "Y");
					session.removeAttribute("USER_SNS_PROGRESS");
				}
			}

			//응답헤더 추가
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0); // Proxies.

		} else {
			//log.debug("==================== ??? ======================");
		}
	}
}

