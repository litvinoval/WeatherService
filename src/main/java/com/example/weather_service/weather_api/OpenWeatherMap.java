package com.example.weather_service.weather_api;


import com.example.weather_service.ServerException;
import com.example.weather_service.holders.WorkProps;
import com.example.weather_service.pojo.server_db.AdditionalParamWeather;
import com.example.weather_service.pojo.server_db.MainParamWeather;
import com.example.weather_service.pojo.server_db.ReceiveData;
import com.example.weather_service.pojo.server_client.WeatherCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
    Класс, в котором реализуется основная логика приложения
 */
@Component
public class OpenWeatherMap {
    /*
        Объект RestTemplate для отправки запросов к API погоды и получения ответов
        типа JSON
    */
    private RestTemplate restTemplate;
    /*
        Связывает данный класс с файлом конфигурации
     */
    private WorkProps workProps;

    @Autowired
    public OpenWeatherMap(RestTemplate restTemplate,
                          WorkProps workProps){
        this.restTemplate = restTemplate;
        this.workProps = workProps;
    }

    public synchronized WeatherCondition getData(
                String city, WeatherCondition weatherCondition)
                                            throws ServerException {

        return workProps.getMode() == 0 ?
                this.getRealData(city, weatherCondition) :
                    this.getMockData(weatherCondition);
    }

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
    private WeatherCondition getMockData(
                WeatherCondition weatherCondition) throws ServerException {

        long temp = (long) (Math.random()
                * System.currentTimeMillis());
        temp = temp % 2 == 0 ?
                temp % 50 : -temp % 50;

        int typeOfPrecipitation = (int) (Math.random()
                * System.currentTimeMillis() % 4);
        weatherCondition.setTemp(String.valueOf(temp));

        weatherCondition.setPrecipitation(
                convertTypeToString(typeOfPrecipitation));

        weatherCondition.setCity(
                temp < 0 ? "Moscow" : "Petersburg");

        return weatherCondition;
    }
    /*
        Получение реальных данных
            Выполняется запрос к API сервиса погоды, запрос возвращает данные в формате
        JSON. Из полученных данных извлекается объект типа ReceiveData.
            Далее из полей полученного объекта извлекаются объекты MainParamWeather и AdditionalParamWeather,
        в полях которых лежат интересующие нас значения.
     */
    private WeatherCondition getRealData(String city,
                                        WeatherCondition weatherCondition){
        int id = city.equals("Moscow") ?
                                524901 : 498817;
        String url = "https://api.openweathermap.org/data/2.5/weather" +
                "?id=" + id +
                "&exclude=current" +
                "&appid=" + workProps.getKey();

        ResponseEntity<ReceiveData> response =
                restTemplate.getForEntity(url, ReceiveData.class);
        MainParamWeather mpw = response
                .getBody().getMain();
        AdditionalParamWeather apw = response
                .getBody().getWeather().get(0);

        weatherCondition
                .setTemp(String.valueOf(mpw.getTemp() - 272));
        weatherCondition
                .setPrecipitation(apw.getDescription());
        weatherCondition
                .setCity(city);
        return weatherCondition;
    }

    /*
        Конвертация числа в описание погоды
        Используется при получении случайных данных о погоде
     */

    private String convertTypeToString(int type)
            throws ServerException{
        switch (type){
            case 0:{
                return "cloud";
            }
            case 1:{
                return "rain";
            }
            case 2:{
                return "sun";
            }
            case 3:{
                return "snow";
            }
        }
        throw new ServerException();
    }

}
