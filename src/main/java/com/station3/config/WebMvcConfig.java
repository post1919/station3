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
				.addPathPatterns("/event/*", "/kkultip/*")
				.excludePathPatterns("/login/*")
				.excludePathPatterns("/castingn/static/**","/lib/**", "/error", "/error/**" ,"/castingn/common/**", "/static/css/**", "/static/js/**", "/static/html/**" ,"/*.html, /*.js, /*.css, /*.jpg, /*.jpeg, /*.png, /*.gif");

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

		//webjars
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

		//업로드 파일 경로 설정
		registry.addResourceHandler("/image/**").addResourceLocations("file:/var/www/castingn/images/");
		registry.addResourceHandler("/files/**").addResourceLocations("file:/var/www/castingn/files/");
		registry.addResourceHandler("/upload/**").addResourceLocations("file:/var/www/castingn/upload/");

		//PHP쪽 파일/이미지들
		registry.addResourceHandler(ConstantProperties.PATH_CASTING_VIEW + "**").addResourceLocations("file:"+ ConstantProperties.PATH_CASTING);

		//견적및  Buyer 데이타
		registry.addResourceHandler("/Buyr/**").addResourceLocations("file:/home/casting/casting/Buyr/files/");
	}

	@Bean
	public BeanNameViewResolver beanNameViewResolver() {
		final BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(2);
		return beanNameViewResolver;
	}

	/*@Bean
	public TilesConfigurer tilesConfigurer() {

		final TilesConfigurer configurer = new TilesConfigurer();

		configurer.setDefinitions(new String[] {"WEB-INF/tiles/tiles.xml"});

		configurer.setCheckRefresh(true);

		return configurer;
	}

	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		tilesViewResolver.setOrder(3);

		//redirect할때 https -> http로 풀리는 현상 수정 => nginx설정파일에서 변경함 20220427 신동아
		//tilesViewResolver.setRedirectHttp10Compatible(false);

		return tilesViewResolver;
	}*/

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
