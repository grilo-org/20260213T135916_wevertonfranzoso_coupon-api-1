package com.example.couponapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI couponOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Coupon Management API")
                        .description("API para gerenciamento de cupons")
                        .version("1.0.0"));
    }
}
