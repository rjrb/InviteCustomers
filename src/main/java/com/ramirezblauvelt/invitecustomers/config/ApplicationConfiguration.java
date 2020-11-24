package com.ramirezblauvelt.invitecustomers.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {

	@Bean
	ApplicationProperties applicationProperties() {
		return new ApplicationProperties();
	}

}
