package com.example.microservicioparent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@EnableSwagger2WebFlux
@SpringBootApplication
@EnableCircuitBreaker

public class MicroServicioParentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServicioParentApplication.class, args);
	}
}
