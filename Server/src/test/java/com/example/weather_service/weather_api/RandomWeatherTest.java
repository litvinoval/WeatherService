package com.example.weather_service.weather_api;

import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;
import com.example.protocol.DTO.enums.Cities;
import com.example.weather_service.exceptions.ServerException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class RandomWeatherTest {
    @Autowired
    private RandomWeather randomWeather;
    @Test
    void getRandomData() throws ServerException {
        WeatherRequest weatherRequest = new WeatherRequest(
                                        113, Cities.PETERSBURG);

        WeatherResponse weatherResponse = randomWeather
                                            .getRandomData(weatherRequest);
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
                113, weatherResponse.getCount());
        assertNotNull(
                weatherResponse.getTemp());
        assertNotNull(
                weatherResponse.getDescription());
        Assert.assertEquals(
                "PETERSBURG", weatherResponse.getCity().toString());
    }
}