package com.station3.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.station3.common.ConstantProperties;
import com.station3.intercentor.AuthInterceptor;
import com.station3.intercentor.LoginInterceptor;
import com.station3.login.util.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/*@Value("${resources.notload.list}")
	private List<String> notLoadList;*/

	private final LoginUserArgumentResolver loginUserArgumentResolver;

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
		"classpath:/static/", "classpath:/public/", "classpath:/", "classpath:/resources/"
	};


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("https://stgwpaystd.inicis.com","https://wpaystd.inicis.com/", "https://dev.workmarket9.com/", "https://biz.workmarket9.com/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		//String pattern = "/castingn/static/**, /lib/**, /error, /error/**, /*.html, /*.js, /*.css, /*.jpg, /*.jpeg, /*.png, /*.gif";

		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/*")
				.excludePathPatterns("/login/*")
				.excludePathPatterns("/static/css/**", "/static/js/**", "/*.html, /*.js, /*.css, /*.jpg, /*.jpeg, /*.png, /*.gif");

		registry.addInterceptor(authInterceptor())
				.excludePathPatterns("/castingn/static/**","/lib/**", "/error", "/error/**" ,"/castingn/common/**", "/static/css/**", "/static/js/**", "/static/html/**");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		// 우선순위를 가장 높게 잡는다.
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}

	@Bean
	public BeanNameViewResolver beanNameViewResolver() {
		final BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(2);
		return beanNameViewResolver;
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		final AuthInterceptor authInterceptor = new AuthInterceptor();
		return authInterceptor;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
		argumentResolvers.add(loginUserArgumentResolver);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.registerModules(new JavaTimeModule(), new Jdk8Module());
		return mapper;
	}

}
