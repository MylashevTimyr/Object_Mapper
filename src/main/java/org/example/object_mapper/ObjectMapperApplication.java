package org.example.object_mapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.example.object_mapper")
public class ObjectMapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ObjectMapperApplication.class, args);
    }

}
