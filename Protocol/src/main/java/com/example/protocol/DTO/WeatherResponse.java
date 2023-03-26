package com.example.protocol.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class WeatherResponse {
    @NonNull
    private int count;
    private int temp;
    private Description description;
    private Cities city;
}