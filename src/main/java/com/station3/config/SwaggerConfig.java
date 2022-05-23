package com.station3.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @screen    :
 * @program   :
 * @author    : 신동아
 * @since     : 2022-05-23
 * @desc      : Swagger 설정
 * @table     :
 * @interface :
 * @param     :
 * @return    :
 * @remark    :
 *
 */
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {

    private static final String API_NAME = "Station3 Assignment API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Station3 Assignment API 명세서";

    @SuppressWarnings("deprecation")
    @Bean
    public Docket api() {

        final List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder().code(200).message("OK").build(),
                new ResponseMessageBuilder().code(400).message("Bad Request").build(),
                new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
                new ResponseMessageBuilder().code(403).message("Forbidden").build(),
                new ResponseMessageBuilder().code(500).message("Internal Error").build());

        Parameter parameterBuilder = new ParameterBuilder()
            .name(HttpHeaders.AUTHORIZATION)
                .description("Access Tocken")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        List<Parameter> globalParamters = new ArrayList<>();
        globalParamters.add(parameterBuilder);

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalOperationParameters(globalParamters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**"))
                .build()
                .securitySchemes(Arrays.asList(new ApiKey("Bearer+accessToken", "Authorization", "header")));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }
}