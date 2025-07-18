package com.isaacAnco.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            final String securitySchemeName = "bearerAuth";
            return new OpenAPI()
                    .info(new Info()
                            .title("Inventory Management API")
                            .version("1.0")
                            .description("API Documentation for Inventory Management System")
                            .contact(new Contact()
                                    .name("Isaac Anco")
                                    .email("your.email@example.com")
                                    .url("https://yourwebsite.com"))
                            .license(new License()
                                    .name("Apache 2.0")
                                    .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                    .addSecurityItem(new SecurityRequirement()
                            .addList(securitySchemeName))
                    .components(new Components()
                            .addSecuritySchemes(securitySchemeName,
                                    new SecurityScheme()
                                            .name(securitySchemeName)
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")));
        }
}
