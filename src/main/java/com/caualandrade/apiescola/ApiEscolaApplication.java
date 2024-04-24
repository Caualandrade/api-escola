package com.caualandrade.apiescola;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "api-escola",version = "1",description = "API desenvolvida para estudo, aplicada em um cenario academico"))
public class ApiEscolaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiEscolaApplication.class, args);
	}

}
