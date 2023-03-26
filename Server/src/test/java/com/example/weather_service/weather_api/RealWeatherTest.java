package com.example.weather_service.weather_api;

import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;
import com.example.protocol.DTO.enums.Cities;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RealWeatherTest {
    @Autowired
    RealWeather realWeather;
    @Test
    void getRealData() {
        WeatherRequest weatherRequest = new WeatherRequest(
                                            3, Cities.MOSCOW);
        WeatherResponse weatherResponse = realWeather
                                                .getRealData(weatherRequest);
        /*
            Проверки:
            1. Что тип возвращаемого значения не null
            2. Номер, встроенный в ответ, совпадает с номером, которым
             тестовое приложение-клиент помечает запрос
            3. Вернулось настоящее значение температура
            4. Вернулось настоящее состояние погоды
            5. Вернулась погода для запрашиваемого города
         */
        assertNotNull(weatherResponse);
        Assert.assertEquals(
                3, weatherResponse.getCount());
        assertNotNull(
                weatherResponse.getTemp());
        assertNotNull(
                weatherResponse.getDescription());
        Assert.assertEquals(
                "MOSCOW", weatherResponse.getCity().toString());
    }
}