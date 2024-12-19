package com.exercise.electricitybill.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@OpenAPIDefinition(
//        info=@Info(
//                title="My API",
//                version="2.0.0",
//                description = "description api"
//        ),
//        servers = {
//                @Server(url="http://localhost:8088",description = "local development server"),
//              //  @Server(url="http://45.117.179.16:8088",description = "production server")
//        }
//)
//@SecurityScheme(
//        name="bearer-key",
//        type= SecuritySchemeType.HTTP,
//        scheme="bearer",
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
@Configuration
public class OpenApiConfig {
        @Bean
        public OpenAPI openAPI(
                @Value("${openapi.service.title}") String title,
                @Value("${openapi.service.version}") String version,
                @Value("${openapi.service.server}") String serverUrl) {
                return new OpenAPI()
                        .servers(List.of(new Server().url(serverUrl)))
                        .info(new Info().title(title)
                                .description("API documents")
                                .version(version)
                                .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                        .components(new Components().addSecuritySchemes("bearerAuth",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                        .bearerFormat("JWT")))
                        .security(List.of(new SecurityRequirement().addList("bearerAuth")));
        }
}
