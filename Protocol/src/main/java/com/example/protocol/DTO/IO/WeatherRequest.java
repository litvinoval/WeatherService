package com.example.protocol.DTO.IO;

import com.example.protocol.DTO.enums.Cities;
import lombok.*;
/*
    Объекта данного класса встраиваются в запрос клиента серверу
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRequest {
    private int count;
    private Cities city;
}
