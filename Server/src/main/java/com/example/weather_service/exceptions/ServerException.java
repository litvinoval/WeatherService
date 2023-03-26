package com.example.weather_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/*
        Исключение, которое выбрасывается при некорректной работе метода
    convertTypeToString() в объекте openWeatherMap
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends IOException {
}
