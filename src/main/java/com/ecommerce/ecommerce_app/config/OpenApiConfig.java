package com.ecommerce.ecommerce_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Ecommerce API")
                        .version("1.0")
                        .description("API documentation for the Ecommerce application"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")));
    }


    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("ecommerce-api")
                .pathsToMatch("/api/**")
                .build();
    }
}
