package com.product.fcm.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.product.fcm.example",
                "com.product.fcm.client"
        }
)
@EnableAutoConfiguration
public class FcmClientExampleApplication {

    public static void main(String[] args) {

        SpringApplication.run(FcmClientExampleApplication.class, args);
    }

}
