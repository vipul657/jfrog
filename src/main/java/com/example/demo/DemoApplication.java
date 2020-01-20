package com.example.demo;

import com.example.demo.consumer.service.MessageProcessor;
import com.example.demo.producer.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Currently buiding both consumer and producer in same service , but in production environments they would be different services
 *
 * */
@SpringBootApplication
public class DemoApplication {


	@Autowired
	StorageService storageService;

	@Autowired
	MessageProcessor messageprocessor;

	public static void main(String[] args) {
		System.out.println("Started Service");
	}

}
