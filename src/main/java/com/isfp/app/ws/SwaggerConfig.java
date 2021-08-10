package com.isfp.app.ws;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(
		title = "Users API", version = "1.0", description = "Users Information API",
		contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Shorouk Abdelaziz", url = "https://shorouk.dev", email = "contact@shorouk.dev")))
@SecurityScheme(name = "Authorization", bearerFormat = "JWT", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)

public class SwaggerConfig {

}
