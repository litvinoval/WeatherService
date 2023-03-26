package com.example.protocol.interfaces;

import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface IWeatherService {

    WeatherResponse getData(WeatherRequest weatherRequest)
            throws InterruptedException, ExecutionException, TimeoutException;

}
