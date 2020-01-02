package com.gfstabile.java.carrest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The main configuration class
 *
 * @author G. F. Stabile
 */
@Configuration
public class ApiRestConfiguration {

    /**
     * Creates a bean of {@link RestTemplate}
     *
     * @return the {@link RestTemplate} instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
