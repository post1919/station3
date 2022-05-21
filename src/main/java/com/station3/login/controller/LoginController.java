package com.station3.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;


@RequiredArgsConstructor
@Controller
@RequestMapping("login")
public class LoginController {

//    private final JoinServiceImpl joinService;

    @RequestMapping("/userLogin")
    public ModelAndView login(String returnURL, String invalidate
            , HttpServletRequest request
            , HttpServletResponse response
//            , UserCompanyRequestDto userCompanyRequestDto
    ) throws Exception {
        ModelAndView mav = new ModelAndView("login/userLogin");

        HttpSession session = request.getSession();

        //로그아웃 처리
        if( Objects.nonNull(invalidate) && invalidate.toUpperCase().equals("Y") ){
            session.invalidate();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        }

        if(!StringUtils.isEmpty(returnURL) ){
            mav.addObject("returnURL", returnURL.replace("amp;", ""));
        }

        /*//스마트핀클럽 가입일경우 USER_COMPANY.USER_COMPANY_SEQ
        if( Objects.nonNull(userCompanyRequestDto.getUserCompanySeq()) ){
            //기업고객 - 회사정보
            UserCompanyResponseDto userCompanyResponse = joinService.getUserCompanyByUserCompanySeq(userCompanyRequestDto);
            mav.addObject("userCompany", userCompanyResponse);
        }*/

        return mav;
    }

}
