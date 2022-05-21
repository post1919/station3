package com.station3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@ServletComponentScan
@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
		return application.sources(MainApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
