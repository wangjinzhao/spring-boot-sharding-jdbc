package com.gold.wang.sharding.jdbc.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by abin on 2018/8/16.
 * http://localhost:9061/swagger-ui.html
 * http://localhost:9061/v2/api-docs
 * 生产环境不启用swagger文档
 * https://blog.csdn.net/u014087707/article/details/79347153
 */
@Configuration
public class SwaggerConfig {
    @Autowired
    private Environment env;
    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Bean
    public Docket buildDocket() {
        String localPort = env.getProperty("server.port");
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(buildApiInf())
                .host("localhost:" + localPort)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.abin.lee.sharding.jdbc.controller"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                .termsOfServiceUrl("https://github.com/wangjinzhao/spring-boot-sharding-jdbc")
                //创建人
                .contact(new Contact("abin", "https://github.com/wangjinzhao", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API DESC")
                .build();
    }
}
