package com.gfstabile.java.carrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({
    "classpath:application.properties", "classpath:api-url.properties"
})
public class CarRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRestApplication.class, args);
    }

}
