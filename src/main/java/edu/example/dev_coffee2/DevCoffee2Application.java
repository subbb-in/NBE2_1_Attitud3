package edu.example.dev_coffee2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class DevCoffee2Application {

	public static void main(String[] args) {
		SpringApplication.run(DevCoffee2Application.class, args);
	}

}
