package com.station3.intercentor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	protected Log log = LogFactory.getLog(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = true;

        /*HttpSession session = request.getSession();
        String userSnsProgress = "";

        if(Objects.nonNull(session.getAttribute("USER_SNS_PROGRESS")) ){
            userSnsProgress = (String) session.getAttribute("USER_SNS_PROGRESS");
        }

        try {
            if( userSnsProgress.equals("Y") ){
                response.sendRedirect(request.getContextPath() + "/join");
                flag = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*HttpSession session = request.getSession();
        String webRoot = request.getContextPath();
        String userSnsProgress = "";

        if(Objects.nonNull(session.getAttribute("USER_SNS_PROGRESS")) ){
            userSnsProgress = (String) session.getAttribute("USER_SNS_PROGRESS");
        }

        try {
            if( userSnsProgress.equals("Y") ){
                response.sendRedirect(webRoot + "/join");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
	};
}