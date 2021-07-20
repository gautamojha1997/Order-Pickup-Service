package com.example.orderpickup.configuration.Swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*
     * URL - http://localhost:8080/api/swagger-ui/
     * */

    @Bean
    //Springfox’s, primary api configuration mechanism is initialized for swagger specification 2.0
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select() //returns an instance of ApiSelectorBuilder
                .apis(RequestHandlerSelectors.any()) //allows selection of RequestHandler's using a predicate
                .paths(PathSelectors.any()) //allows selection of Path's using a predicate
                .build(); //built after configuring the api and path selectors
    }
}
