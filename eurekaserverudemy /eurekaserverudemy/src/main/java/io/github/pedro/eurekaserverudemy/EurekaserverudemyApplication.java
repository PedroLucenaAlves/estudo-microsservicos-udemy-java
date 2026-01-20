package io.github.pedro.eurekaserverudemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverudemyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaserverudemyApplication.class, args);
	}

}
