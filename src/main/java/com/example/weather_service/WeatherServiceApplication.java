package com.example.weather_service;

import com.example.weather_service.holders.WorkProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties(WorkProps.class)
public class WeatherServiceApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(WeatherServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
