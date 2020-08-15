package com.appsdeveloperblog.photoapp.api.albums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PhotoAppApiAlbumsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiAlbumsApplication.class, args);
	}

}

