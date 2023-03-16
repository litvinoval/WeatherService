package com.example.weather_service.pojo;

import lombok.Data;

import java.util.List;

/*
    Класс, который получает данные из формата JSON при обращении
    к API сервиса погоды.
 */
@Data
public class ReceiveData {
    private List<AdditionalParamWeather> weather;
    private MainParamWeather main;
}
