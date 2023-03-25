package com.example.weather_service.controllers;

import com.example.protocol.DTO.WeatherRequest;
import com.example.protocol.DTO.WeatherResponse;
import com.example.weather_service.ServerException;
import com.example.weather_service.weather_api.OpenWeatherMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/*
    Контроллер для взаимодействия с брокером сообщений
 */
@Controller
@RequestMapping
public class MainController {

    /*
        OpenWeatherMap - объект, реализующий основную логику программы, выполняет
        запрос к API сервиса погоды или выдает случайные данные о погоде, в зависимости от
        настроек сервера
    */
    private OpenWeatherMap openWeatherMap;

    @Autowired
    public MainController(
            OpenWeatherMap openWeatherMap){
        this.openWeatherMap = openWeatherMap;
    }

    /*
        Взаимодействие с брокером сообщений
        MessageMapping - получение сообщений через WebSocket
        SendTo - в какой топик будем складывать ответ
            Вызывается метод getData объекта openWeatherMap, который возвращает
        интересующие данные о погоде.
     */
    @MessageMapping("/{city}")
    @SendTo("/topic/{city}")
    public WeatherResponse getWeather(
            @DestinationVariable String city,
            WeatherRequest weatherRequest) throws ServerException {

        city = city.equalsIgnoreCase(
                    Cities.MOSCOW.toString()) ?
                        "Moscow" : "Petersburg";

        return openWeatherMap
                .getData(city, weatherRequest);
    }

    enum Cities{
        MOSCOW,
        SAINT_PETERSBURG
    }

}
