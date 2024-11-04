package com.github.overz;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		Dotenv.configure()
			.ignoreIfMissing()
			.systemProperties()
			.load()
		;

		SpringApplication.run(Main.class, args);
	}
}
