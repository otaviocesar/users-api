package com.mlb.usersapi.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "Users API",
		version = "v1",
		description = "This app provides REST APIs for user entities",
		contact = @Contact(
			name = "OTAVIO C C SANTOS",
			email = "otaviocesarcostasantos@gmail.com",
			url = "https://github.com/otaviocesar/users-api"
		)
	)
)
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(
				new Components().addSecuritySchemes(
					"bearer-jwt",
					new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
						.in(SecurityScheme.In.HEADER)
						.name("Authorization")
				)
			)
			.addSecurityItem(
				new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write"))
			);
	}
}
