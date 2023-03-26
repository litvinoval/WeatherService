package com.example.weather_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
 /*
    Исключение, возникающие при получении названия города, отличного от Москвы и Санкт-Петербурга, в методе
     getWeather объекта MainController
  */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityNotFoundException extends RuntimeException{
}
