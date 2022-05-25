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

        if (!passwordEncoder.matches(userPw, customUserDetailsDto.getUpasswd())) {
            log.error("비번 일치 안함 JAVA");
        }

        return new UsernamePasswordAuthenticationToken(customUserDetailsDto, customUserDetailsDto, customUserDetailsDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }*/

}
