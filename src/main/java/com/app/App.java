package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com"})
@EntityScan("com")
@EnableJpaRepositories("com")
/**
 * Starting point of the application. Dependency Injection
 * @author benat
 *
 */
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

