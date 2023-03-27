package com.example.weather_service.weather_api;

import com.example.protocol.DTO.enums.Description;
import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;
import com.example.weather_service.holders.WorkProps;
import com.example.weather_service.pojo.AdditionalParamWeather;
import com.example.weather_service.pojo.MainParamWeather;
import com.example.weather_service.pojo.ReceiveData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
    Класс, возвращающий реальные данные о погоде
 */
@Component
public class RealWeather {

    private final RestTemplate restTemplate;
    /*
        Связывает данный класс с файлом конфигурации
     */
    private final WorkProps workProps;

    @Autowired
    public RealWeather(RestTemplate restTemplate,
                       WorkProps workProps){
        this.restTemplate = restTemplate;
        this.workProps = workProps;
    }
    /*
        Получение реальных данных
            Выполняется запрос к API сервиса погоды, запрос возвращает данные в формате
        JSON. Из полученных данных извлекается объект типа ReceiveData.
            Далее из полей полученного объекта извлекаются объекты MainParamWeather и AdditionalParamWeather,
        в полях которых лежат интересующие нас значения.
     */

    public WeatherResponse getRealData(
            WeatherRequest weatherRequest){
        WeatherResponse weatherResponse = new WeatherResponse();
        int id = weatherRequest.getCity().getName().equals("Moscow") ?
                524901 : 498817;
        String url = "http://api.openweathermap.org/data/2.5/weather" +
                "?id=" + id +
                "&exclude=current" +
                "&appid=" + workProps.getKey();

        ResponseEntity<ReceiveData> response =
                restTemplate.getForEntity(url, ReceiveData.class);
        MainParamWeather mpw = response
                .getBody().getMain();
        AdditionalParamWeather apw = response
                .getBody().getWeather().get(0);
        weatherResponse
                .setCount(weatherRequest.getCount());
        weatherResponse
                .setTemp(mpw.getTemp() - 272);
        weatherResponse
                .setDescription(Description.valueOf(
                        changeLine(apw.getDescription())));
        weatherResponse
                .setCity(weatherRequest.getCity());
        return weatherResponse;
    }

    /*
            Изменяет получененное описание погоды, с строчными буквами и пробелами между слов,
        в формат константы для enum Description
     */
    private String changeLine(String description){
        if(description.contains("dust"))
            return "DUST";
        String[] words = description.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String w : words){
            sb.append(w).append("_");
        }

        return sb.substring(0, sb.length() - 1).toUpperCase();
    }


}
