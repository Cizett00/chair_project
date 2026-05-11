// src/main/java/org/example/PostureApplication.java
package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PostureApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostureApplication.class, args);
    }
}
