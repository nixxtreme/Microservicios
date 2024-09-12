package com.micro.micro_crud.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "companiesCRUD",
                version = "1.0.0",
                description = "Este es un CRUD para el manejo de compañías"
        )
)

public class OpenApiConfig {


}
