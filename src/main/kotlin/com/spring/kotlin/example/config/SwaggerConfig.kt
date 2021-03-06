package com.spring.kotlin.example.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.BasicAuth
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*


@Configuration
@EnableSwagger2
class SpringFoxConfig {

    @Value("\${controller-package}")
    val controllerPackage: String = ""

    @Bean
    fun api(): Docket {
        val schemeList: MutableList<SecurityScheme> = ArrayList()
        schemeList.add(BasicAuth("basicAuth"))
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(controllerPackage))
                .paths(PathSelectors.any())
                .build().securitySchemes(schemeList)
    }
}