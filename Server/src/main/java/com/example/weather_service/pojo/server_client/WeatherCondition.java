package com.example.weather_service.pojo.server_client;

import com.example.protocol.DTO.Cities;
import com.example.protocol.DTO.Description;
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

    private int temp;
    private Description description;
    private Cities city;

}