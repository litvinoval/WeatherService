package com.example.clientapp;

import com.example.clientlibrary.DataTransmitter;
import com.example.protocol.DTO.enums.Cities;
import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
/*
        Класс, эмитрующий действия клиента, вызывает объект DataTransmitter, который, в свою очередь, устанавливает
    диалог клиент-сервер
 */
@Component
public class Printer implements Runnable{
    private int count;

    private final DataTransmitter dataTransmitter;

    public Printer(){
        dataTransmitter = new DataTransmitter();
    }

    @Override
    public void run() {

            while(true){
                try {
                    String city = System.currentTimeMillis() % 2 == 0 ?
                        "Moscow" : "Petersburg";

                    WeatherResponse weatherResponse = dataTransmitter.getData(
                            new WeatherRequest(count++, Cities.valueOf(city.toUpperCase())));

                    print(weatherResponse);
                    Thread.sleep(10013);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);

                } catch (ExecutionException e) {
                    throw new RuntimeException(e);

                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
    }
    /*
        метод для печати к консоль
     */
    private void print(WeatherResponse weatherResponse){
        StringBuilder sb = new StringBuilder();
        sb.append("count=").append(
                weatherResponse.getCount()).append(", ");

        sb.append("temp=").append(
                weatherResponse.getTemp()).append(", ");

        sb.append("description=").append(
                weatherResponse.getDescription().getName()).append(", ");

        sb.append("city=").append(
                weatherResponse.getCity().getName());
        System.out.println(sb);
    }
}
