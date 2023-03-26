package com.example.weather_service.weather_api;

import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;
import com.example.weather_service.exceptions.ServerException;
import com.example.weather_service.holders.WorkProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
    Класс, выбирающий, какие данные, реальные или случайные, вернуться клиенту, в зависимости от настроек сервера
 */
@Component
public class WeatherSelector {

    private final RandomWeather randomWeather;
    private final RealWeather realWeather;
    private final WorkProps workProps;
    @Autowired
    public WeatherSelector(WorkProps workProps,
                           RealWeather realWeather,
                           RandomWeather randomWeather){
        this.workProps = workProps;
        this.realWeather = realWeather;
        this.randomWeather = randomWeather;
    }
    /*
        Метод, который выполняет запрос к API погоды
     */
    public WeatherResponse getData(
            WeatherRequest weatherRequest)
            throws ServerException {

        return workProps.getMode() == 0 ?
                realWeather.getRealData(weatherRequest) :
                randomWeather.getRandomData(weatherRequest);
    }

}
