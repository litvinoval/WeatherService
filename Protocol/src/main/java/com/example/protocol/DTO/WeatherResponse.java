package com.example.protocol.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class WeatherResponse {

    @NonNull
    private int count;

    private String temp;
    private String precipitation;
    private String city;
}