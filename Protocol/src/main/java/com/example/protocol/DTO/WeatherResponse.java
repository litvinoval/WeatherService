package com.example.protocol.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private int count;
    private int temp;
    private Description description;
    private Cities city;
}