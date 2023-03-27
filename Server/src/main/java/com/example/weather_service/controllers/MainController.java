package com.example.weather_service.controllers;

import com.example.protocol.DTO.enums.Cities;
import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;
import com.example.weather_service.exceptions.ServerException;
import com.example.weather_service.exceptions.CityNotFoundException;
import com.example.weather_service.weather_api.WeatherSelector;
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
        weathеальнerSelector - объект класса, который выбирает, какие данные о погоде, реальные или случайные, должен
        получить клиент
    */
    private final WeatherSelector weatherSelector;
    @Autowired
    public MainController(WeatherSelector weatherSelector) {
        this.weatherSelector = weatherSelector;
    }

    /*
            Взаимодействие с брокером сообщений
            MessageMapping - получение сообщений через WebSocket
            SendTo - в какой топик будем складывать ответ
                Вызывается метод getData объекта weatherSelector, который возвращает
            интересующие данные о погоде.
                Результаты для каждого из городов складываются в определенный топик, чтобы данные о погоде в разных
            городых не перемешивались
         */
    @MessageMapping("/{city}")
    @SendTo("/topic/{city}")
    public WeatherResponse getWeather(
            @DestinationVariable String city,
            WeatherRequest weatherRequest) throws ServerException {

        if(!city.equalsIgnoreCase(Cities.MOSCOW.getName()) &&
                !city.equalsIgnoreCase(Cities.PETERSBURG.getName()))
            throw new CityNotFoundException();

        return weatherSelector.getData(weatherRequest);
    }


}
