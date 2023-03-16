package com.example.weather_service.controllers;

import com.example.weather_service.ServerException;
import com.example.weather_service.holders.WorkProps;
import com.example.weather_service.pojo.WeatherCondition;
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
@RequestMapping("/main")
public class MainController {
    /*
        Объект, связывающий контроллер и файл конфигурации
    */
    private WorkProps workProps;
    /*
        Объект, реализующий основную логику программы, выполняет запрос к API
        сервиса погоды или выдает случайные данные о погоде, в зависимости от
        настроек сервера
    */
    private OpenWeatherMap openWeatherMap;

    @Autowired
    public MainController(WorkProps workProps,
                          OpenWeatherMap openWeatherMap){
        this.workProps = workProps;
        this.openWeatherMap = openWeatherMap;
    }

    /*
        Взаимодействие с брокером сообщений
        Если поле mode класса WorkProps установлено в 0, то будут возвращаться
        реальные данные, иначе случайные
        MessageMapping - получение сообщений через WebSocket
        SendTo - в какой топик будем складывать ответ
     */
    @MessageMapping("/{city}")
    @SendTo("/topic/{city}")
    public WeatherCondition getWeather(
            @DestinationVariable String city,
            WeatherCondition weatherCondition) throws ServerException {

        city = city.equalsIgnoreCase(Cities.MOSCOW.toString()) ?
                "Moscow" : "Petersburg";
        return workProps.getMode() == 0 ?
                openWeatherMap.getRealData(city, weatherCondition) :
                    openWeatherMap.getMockData(weatherCondition);
    }

    enum Cities{
        MOSCOW,
        SAINT_PETERSBURG
    }

}
