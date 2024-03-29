//package com.hfut.newsbackend.config;
//
///**
// * @author Lucky
// * @description: TODO
// * @date 2022/3/15 11:39
// */
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class Swagger2 {
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.hfut.newsbackend.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        Contact contact = new Contact("Lucky", "", "2214187440@qq.com");
//        return new ApiInfoBuilder()
//                .title("服务:发布为daocke镜像,权限管理，用户管理，页面管理，日志 后台 APIs")
//                .description("服务:发布为daocke镜像,权限管理，用户管理，页面管理，日志 后台")
//                .termsOfServiceUrl("http://192.168.1.198:10070/platformgroup/ms-admin")
//                .contact(contact)
//                .version("1.0")
//                .build();
//    }
//
//}
//
