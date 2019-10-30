package com.a.spammer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpammerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpammerApplication.class, args);
    }

}
