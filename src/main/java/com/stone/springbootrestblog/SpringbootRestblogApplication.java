package com.stone.springbootrestblog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot REST APIs",
				description = "Spring Boot Blog App REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Ajay Pawar",
						email = "pawarajay138@gmail.com",
						url = "http://localhost:8080/api/posts"
				),
				license = @License(
						name = "Apache 2.0",
						url = "www.springdoc.in"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App REST API Documentation doc",
				url = "http://localhost://swagger-ui/index.html"
		)
)
public class SpringbootRestblogApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestblogApplication.class, args);
	}

}
