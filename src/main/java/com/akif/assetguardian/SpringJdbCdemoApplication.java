package com.akif.assetguardian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringJdbCdemoApplication {

	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(SpringJdbCdemoApplication.class, args);
	}

}
