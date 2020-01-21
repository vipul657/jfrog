package com.example.jfrog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Currently building both consumer and producer in same service , but in production environments they would be different services
 *
 **/

@SpringBootApplication
@EnableConfigurationProperties
public class DemoApplication{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}
}
