package com.example.weather_service.pojo;

import lombok.*;

/*
    Класс, с помощью которого обеспечивается взаимодействие
    клиентского приложения и сервера.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class WeatherCondition {

    @NonNull
    private int count;

    private String temp;
    private String precipitation;
}
