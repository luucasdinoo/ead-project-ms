package com.ead.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CoursemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursemsApplication.class, args);
    }

}
