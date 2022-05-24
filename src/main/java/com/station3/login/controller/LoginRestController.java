package com.station3.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.station3.common.ConstantProperties;
import com.station3.common.dto.CommonResponse;
import com.station3.login.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/rest/login", method = {RequestMethod.POST, RequestMethod.GET})
@RestController
public class LoginRestController {

//    private final AuthenticationManager authenticationManager;

    @ResponseBody
    @PostMapping(value = "/asyncLogin", produces = "application/json")
    public ResponseEntity<?> userLogin(
            @RequestParam("username") String username
            , @RequestParam("password") String password
            , HttpServletRequest request, HttpServletResponse response
    ) throws JsonProcessingException {

        String msg = "";

        ValidateUtil.userId(username);
        ValidateUtil.password(username, password, "");

        // 아이디와 패스워드로, Security 가 알아 볼 수 있는 token 객체로 변경한다.
        /*UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // AuthenticationManager 에 token 을 넘기면 UserDetailsService 가 받아 처리하도록 한다.
        Authentication authentication = authenticationManager.authenticate(token);

        // 실제 SecurityContext 에 authentication 정보를 등록한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession();
        CustomUserDetailsDto user = (CustomUserDetailsDto)authentication.getPrincipal();
        session.setAttribute("USER", (CustomUserDetailsDto)authentication.getPrincipal());*/

        CommonResponse commonResponse = CommonResponse.builder()
                //.data(loginResponseDto)
                .message(msg)
                .status(ConstantProperties.OUTPUT_SUCCESS)
                .build();

        return ResponseEntity.ok().body(commonResponse);
    }
}
