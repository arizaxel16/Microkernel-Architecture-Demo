package com.plugin_template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class PluginTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(PluginTemplateApplication.class, args);
	}

	// Define RestTemplate bean to send HTTP requests
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
