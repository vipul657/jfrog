package com.example.demo;

import com.example.demo.consumer.service.MessageProcessor;
import com.example.demo.producer.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


/**
 * Currently building both consumer and producer in same service , but in production environments they would be different services
 *
 * */

@SpringBootApplication
@EnableConfigurationProperties
public class DemoApplication implements ApplicationRunner {

	@Autowired
	Configuration configuration;

	@Autowired
	StorageService storageService;

	@Autowired
	MessageProcessor messageProcessor;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(configuration.getApiKey());
		storageService.startStorage();
	}
}
