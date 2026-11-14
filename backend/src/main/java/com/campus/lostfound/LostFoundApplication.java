package com.campus.lostfound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan */
@SpringBootApplication
public class LostFoundApplication {
    public static void main(String[] args) {
        SpringApplication.run(LostFoundApplication.class, args);
    }
}
