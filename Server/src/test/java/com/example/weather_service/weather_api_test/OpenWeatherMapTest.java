package com.example.weather_service.weather_api_test;

import com.example.weather_service.ServerException;
import com.example.weather_service.holders.WorkProps;
import com.example.weather_service.pojo.server_client.WeatherCondition;
import com.example.weather_service.weather_api.OpenWeatherMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;


/*
    Тест для метода getData() объекта OpenWeatherMap
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenWeatherMapTest {
    @Autowired
    private OpenWeatherMap openWeatherMap;

    @Test
    public void testGetData() throws ServerException {
        WeatherCondition weatherCondition = new WeatherCondition(1);
        weatherCondition = openWeatherMap.getData("Moscow", weatherCondition);
        /*
            Проверки:
            1. Что тип возвращаемого значения не null
            2. Номер, встроенный в ответ, совпадает с номером, которым
             тестовое приложение-клиент помечает запрос
            3. Вернулось настоящее значение температура
            4. Вернулось настоящее состояние погоды
            5. Вернулась погода для запрашиваемого города
         */
        assertNotNull(weatherCondition);
        Assert.assertEquals(
                1, weatherCondition.getCount());
        assertNotNull(
                weatherCondition.getTemp());
        assertNotNull(
                weatherCondition.getPrecipitation());
        Assert.assertEquals(
                "Moscow", weatherCondition.getCity());
    }



}
