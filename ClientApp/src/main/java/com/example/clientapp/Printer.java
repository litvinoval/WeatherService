package com.example.clientapp;

import com.example.clientlibrary.DataTransmitter;
import com.example.protocol.DTO.WeatherResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
public class Printer implements Runnable{

    private final DataTransmitter dataTransmitter;

    public Printer(){
        dataTransmitter = new DataTransmitter();
    }

    @Override
    public void run() {
        try {
            System.out.println("-----");
            WeatherResponse weatherResponse = dataTransmitter.getData();
            System.out.println("=====");
            System.out.println(weatherResponse);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);

        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
