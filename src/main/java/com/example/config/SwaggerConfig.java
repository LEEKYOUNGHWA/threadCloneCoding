package com.example.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Example API Docs",
                description = "Description",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = ""; // "Bearer";

    @Bean
    public GroupedOpenApi user() {
      String[] paths = { "/user/**" };

      return GroupedOpenApi
        .builder()
        .group("user")
        .pathsToMatch(paths)
        .build();
    }
    
    @Bean
    public GroupedOpenApi board() {

    	String[] paths = { "/board/**" };

        return GroupedOpenApi
          .builder()
          .group("board")
          .pathsToMatch(paths)
          .build();
    }
    
    @Bean
    public GroupedOpenApi reply() {

    	String[] paths = { "/reply/**" };

        return GroupedOpenApi
          .builder()
          .group("reply")
          .pathsToMatch(paths)
          .build();
    }
    
    @Bean
    public OpenAPI openAPI() {
        String securityJwtName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
        Components components = new Components()
                .addSecuritySchemes(securityJwtName, new SecurityScheme()
                        .name(securityJwtName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat(securityJwtName));
        
        

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}