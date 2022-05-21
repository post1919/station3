package com.station3.login.handler;

import com.station3.common.ConstantProperties;
import com.station3.login.dto.LoginResponseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Data
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private String username="username"; //HttpServletRequest 에서 로그인 아이디가 저장되어 있는 파라미터 이름. 아이디값이 들어오는 input 태그 name
    private String password="password"; //HttpServletRequest 에서 로그인 비밀번호가 저장되어 있는 파라미터 이름. 비밀번호값이 들어오는 input 태그 name
    private String errormsgName="loginErrorMsg"; //로그인 페이지에서 jstl을 이용하여 에러메시지를 가져올 때 사용할 변수 이름.
    private String defaultFailureUrl; //실패시 보여줄 화면 url

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String memberId = request.getParameter(username);
        String memberPw = request.getParameter(password);
        String errormsg = null;
        defaultFailureUrl = "/login";

        /*if(exception instanceof BadCredentialsException) {
            errormsg = MessageUtils.getMessage("loginErrorMessage.BadCredentials");
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errormsg = MessageUtils.getMessage("loginErrorMessage.BadCredentials");
        } else if(exception instanceof DisabledException) {
            errormsg = MessageUtils.getMessage("loginErrorMessage.Disaled");
        } else if(exception instanceof CredentialsExpiredException) {
            errormsg = MessageUtils.getMessage("loginErrorMessage.CredentialsExpired");
        } else if(exception instanceof AuthenticationException) {
            errormsg = MessageUtils.getMessage("loginErrorMessage.AuthenticationException");
        } else {
            errormsg = MessageUtils.getMessage("loginErrorMessage.Etc");
        }*/

        request.setAttribute(username, memberId);
        request.setAttribute(password, memberPw);
        request.setAttribute(errormsgName, errormsg);
        request.setAttribute("status", ConstantProperties.OUTPUT_FAIL);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        /*loginResponseDto.setCode(ConstantProperties.RESPONSE_STATUS_200);
        loginResponseDto.setStatus(ConstantProperties.RESPONSE_STATUS_200);*/
        loginResponseDto.setResult(ConstantProperties.OUTPUT_FAIL);

        request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);
    }
}
