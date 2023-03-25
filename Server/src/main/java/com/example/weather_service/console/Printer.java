package com.example.weather_service.console;

import com.example.weather_service.pojo.server_client.WeatherCondition;
import com.example.weather_service.weather_api.OpenWeatherMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
        Класс, который пишет в консоль каждые 10 секунд информацию,
    получаемую из getData объекта openWeatherMap
 */
@Component
public class Printer implements Runnable{

    private OpenWeatherMap openWeatherMap;

    @Autowired
    public Printer(OpenWeatherMap openWeatherMap){
        this.openWeatherMap = openWeatherMap;
    }

    @Override
    public void run() {
        int count = 0;
        while(true){
            /*
                Выбор города, погода для которого будет выводится
             */
            int type = (int) (Math.random()
                    * System.currentTimeMillis() % 2);
            WeatherCondition weatherCondition = new WeatherCondition(count);
            try {
                if (type == 0) {
                    weatherCondition = openWeatherMap
                                            .getData("Moscow", weatherCondition);
                }else {
                   weatherCondition = openWeatherMap
                                        .getData("Petersburg", weatherCondition);
                }
                count++;
                System.out.println(weatherCondition);
                Thread.sleep(10000);
            } catch (Exception e){
                throw new RuntimeException();
            }

        }
    }
}
