package com.goldenrealstate.gretodo.server;

import com.goldenrealstate.gretodo.rest.config.RestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

/**
 * Application initialization. This uses a SpringBoot annotation and configures a yaml properties file.
 *
 * @author Mathews Motta
 * @since 1.0
 */
@SpringBootApplication
@Import({RestConfig.class})
@Configuration
public class GoldenRealStateToDoApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		System.setProperty("spring.config.name" , "gretodo-server");
		SpringApplication.run(GoldenRealStateToDoApplication.class, args);
	}

}
