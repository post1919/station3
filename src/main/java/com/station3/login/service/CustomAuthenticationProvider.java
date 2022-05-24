package com.station3.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider /*implements AuthenticationProvider*/ {

    /*private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    //private final MessageDigestPasswordEncoder md5Encoder;

    *//*@Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;*//*

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String memberId = token.getName();
        String userPw = (String) token.getCredentials();

        // UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        CustomUserDetailsDto customUserDetailsDto = (CustomUserDetailsDto) userDetailsService.loadUserByUsername(memberId);

        log.info( "getUsername : "+customUserDetailsDto.getUname()  );
        log.info( "getMemberId : "+customUserDetailsDto.getUid()  );
        log.info( "getMemberPw : "+customUserDetailsDto.getUpasswd()  );
        log.info( "userPw : "+userPw  );
        log.info( "getAuthorities : "+customUserDetailsDto.getAuthorities()  );
        log.info( "getMemberPw : "+customUserDetailsDto.getUpasswd()  );

        String secession = Optional.ofNullable(customUserDetailsDto.getSecession()).orElse(""); //탈퇴여부

        *//*
        * 워크마켓9 PHP 사이트는 비번을 MD5 사용하므로
        * 워크마켓9 PHP 사용자를 위해 MD5도 체크함
         *//*
        MessageDigestPasswordEncoder md5Encoder = new MessageDigestPasswordEncoder("MD5");
        if (!passwordEncoder.matches(userPw, customUserDetailsDto.getUpasswd())) {
            log.error("비번 일치 안함 JAVA");

            if( md5Encoder.matches(userPw, customUserDetailsDto.getUpasswd()) ) {
                log.info("PHP LOGIN SUCCESS");
            } else {
                log.error("비번 일치 안함 PHP");
                throw new BadCredentialsException(userPw + " Invalid password");
            }
        }

        //회사링크받고 가입한경우 처리
        if( customUserDetailsDto.getUtype() == 10 ){
            throw new CommonException("승인 대기중이거나 로그인할 수 없는 회원입니다.", ErrorCode.ILLEGAL_STATE);

        } else if( secession.equals("Y") ){
            throw new BadCredentialsException("탈퇴한 회원입니다.");

        //최초 인증을 하지 않은 회원(캐스팅엔/업무마켓9(PHP)
        } else if( Objects.isNull(customUserDetailsDto.getAuthYn()) ){
            throw new CommonException("최초 한번의 회원인증이 필요합니다.", ErrorCode.NEED_AUTH);
        }

        return new UsernamePasswordAuthenticationToken(customUserDetailsDto, customUserDetailsDto, customUserDetailsDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }*/

}
