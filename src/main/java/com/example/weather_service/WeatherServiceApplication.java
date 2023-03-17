package com.example.weather_service;

import com.example.weather_service.console.Printer;
import com.example.weather_service.holders.WorkProps;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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

    /*
        Создается поток для класса Printer, который пишет информацию в консоль
     */
    @Bean
    public ApplicationRunner applicationRunner(Printer printer){
        return args -> {
            Thread thread = new Thread(printer);
            thread.start();
        };
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
