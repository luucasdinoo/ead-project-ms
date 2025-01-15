package com.ead.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NotificationmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationmsApplication.class, args);
    }

}
