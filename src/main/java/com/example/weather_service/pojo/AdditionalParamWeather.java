package com.example.weather_service.pojo;


import lombok.Data;
/*
    Дополнительные параметры погоды, из объекта данного типа извлекается пока что
    поле description
 */
@Data
public class AdditionalParamWeather  {
    private int id;
    private String main;
    private String description;
    private String icon;
}
