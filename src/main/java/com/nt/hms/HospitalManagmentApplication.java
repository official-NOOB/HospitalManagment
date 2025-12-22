package com.nt.hms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class HospitalManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagmentApplication.class, args);
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	 
}
