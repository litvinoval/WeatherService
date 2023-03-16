package com.example.weather_service.mock_data_test;

import com.example.weather_service.ServerException;
import com.example.weather_service.pojo.WeatherCondition;
import com.example.weather_service.weather_api.OpenWeatherMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockDataTest {

    @Autowired
    private OpenWeatherMap openWeatherMap;

    @Test
    public void testGetMockData() throws ServerException {
        WeatherCondition weatherCondition =
                openWeatherMap.getMockData(new WeatherCondition(1));
        assertNotNull(weatherCondition);
        Assert.assertEquals(1, weatherCondition.getCount());
        assertNotNull(weatherCondition.getTemp());
        assertNotNull(weatherCondition.getPrecipitation());
    }

}
