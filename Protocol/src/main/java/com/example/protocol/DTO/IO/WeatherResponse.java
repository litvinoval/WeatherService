package com.example.protocol.DTO.IO;

import com.example.protocol.DTO.enums.Cities;
import com.example.protocol.DTO.enums.Description;
import lombok.*;
/*
    Объекта данного класса встраиваются в ответы сервера клиенту
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private int count;
    private Integer temp;
    private Description description;
    private Cities city;
}