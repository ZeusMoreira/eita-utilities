package com.eitasutilities.cs2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Cs2Application {

	public static void main(String[] args) {
		SpringApplication.run(Cs2Application.class, args);
	}

}
