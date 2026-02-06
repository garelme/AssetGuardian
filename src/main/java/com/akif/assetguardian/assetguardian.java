package com.akif.assetguardian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class assetguardian {

	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(assetguardian.class, args);
	}

}
