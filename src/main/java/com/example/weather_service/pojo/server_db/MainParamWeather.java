package com.example.weather_service.pojo.server_db;

import lombok.Data;

/*
    Основные параметры погоды, из объекта данного типа извлекается пока что
    поле temp
 */
@Data
public class MainParamWeather {
    private int temp;
    private int pressure;
    private int humidity;
}
