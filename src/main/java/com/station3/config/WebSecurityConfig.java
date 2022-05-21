package com.station3.config;

import com.station3.login.filter.CustomAuthenticationFilter;
import com.station3.login.handler.CustomLoginSuccessHandler;
import com.station3.login.handler.LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	private final UserDetailsService userDetailsService;

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //POSTMAN에서 csrf토큰 사용하려면 설정해야함
			.ignoringAntMatchers("/order/inicis/**")
			.ignoringAntMatchers("/order/wpay/**")
			.ignoringAntMatchers("/membership/wpay/**")
			.ignoringAntMatchers("/rest/order/**") //임시
			.ignoringAntMatchers("/rest/order/wpay/**")
			.ignoringAntMatchers("/rest/order/savePinclubOrder")
			.ignoringAntMatchers("/membership/main/**")
			.ignoringAntMatchers("/rest/join/login/loginCheck")
			.and()
				.authorizeRequests()
				.antMatchers("/sourcing/sourcingProcess").authenticated()
				.antMatchers("/mypage/**").authenticated()
				.antMatchers("/user/**").authenticated()
				.antMatchers("/order/**").authenticated()
				.antMatchers("/claim/**").authenticated()
				.antMatchers("/membership/smartPinclub/**").authenticated()
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/", "/css/**", "/images/**", "/js/**", "/profile").permitAll()
				.anyRequest().permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID")
			.and()
				.formLogin()
				.loginPage("/login/userLogin")
				//.failureHandler(authenticationFailureHandler)
				.successForwardUrl("/main")
				.permitAll()
		;

		//외부 도메인 접근 허용
		http.headers().frameOptions().disable()
				.addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM localhost"));
	}

	/*@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
		authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
	}*/

	// 1.
	@Bean
	public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
		customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
		customAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());
		customAuthenticationFilter.afterPropertiesSet();
		return customAuthenticationFilter;
	}

	// 2.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// 3.
	/*@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		// 4.
		return new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder());
	}*/

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CustomLoginSuccessHandler customLoginSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		return new LoginFailureHandler();
	}
}
