package com.gipher.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@SpringBootApplication annotation is used to mark a configuration class that declares one or more @Bean 
//methods and also triggers auto-configuration and component scanning
@SpringBootApplication

//@EnableEurekaServer annotation is used to make your Spring Boot application acts as a Eureka Server
@EnableEurekaClient
public class GipherAppApigatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GipherAppApigatewayServiceApplication.class, args);
	}

}