package com.JobFindingPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.JobFindingPlatform.Entity")
@ComponentScan(basePackages = "com.JObFindingPlatform")
public class JobfindApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobfindApplication.class, args);
	}

}
