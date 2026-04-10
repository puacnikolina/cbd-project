package org.example.exhibitionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class ExhibitionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExhibitionServiceApplication.class, args);
    }

}
