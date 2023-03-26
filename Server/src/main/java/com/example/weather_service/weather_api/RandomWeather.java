package com.example.weather_service.weather_api;

import com.example.protocol.DTO.enums.Description;
import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;
import com.example.weather_service.exceptions.ServerException;
import org.springframework.stereotype.Component;

/*
    Класс, возвращающий случайные данные о погоде
 */
@Component
public class RandomWeather {

    /*
        Получение случайных данных
        Случайное значение температуры получается путем умножения значения,
    возвращаемого Math.random(), на реальное время в миллисекундах, чтобы результат работы
    казался случайным.
        После этого возвращается остаток от деления полученного числа на 50, чтобы получить
    результат в диапазоне реальных температур Земли.
        Получение описания погоды получается путем умножения значения,
    возвращаемого Math.random(), на реальное время в миллисекундах, чтобы результат работы
    казался случайным.
        После этого возвращается остаток от деления полученного числа на 4, чтобы получить
    результат одного из четрых типов погоды.
    Далее значение через switch конвертируется в одно из описаний погоды.
     */


    public WeatherResponse getRandomData(
            WeatherRequest weatherRequest) throws ServerException {

        WeatherResponse weatherResponse = new WeatherResponse();
        long temp = (long) (Math.random()
                * System.currentTimeMillis());
        temp = temp % 2 == 0 ?
                temp % 50 : -temp % 50;

        int typeOfPrecipitation = (int) (Math.random()
                * System.currentTimeMillis() % 4);

        weatherResponse
                .setCount(weatherRequest.getCount());
        weatherResponse
                .setTemp((int)temp);
        weatherResponse
                .setDescription(
                        convertTypeToString(typeOfPrecipitation));
        weatherResponse
                .setCity(weatherRequest.getCity());

        return weatherResponse;
    }

    /*
        Полученное случаныое значение преобразуется в одну из констант Description
     */

    private Description convertTypeToString(int type)
            throws ServerException{
        switch (type){
            case 0:{
                return Description.FEW_CLOUDS;
            }
            case 1:{
                return Description.DRIZZLE_RAIN;
            }
            case 2:{
                return Description.CLEAR_SKY;
            }
            case 3:{
                return Description.SNOW;
            }
        }
        throw new ServerException();
    }

}
