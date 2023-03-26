package com.example.protocol.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class WeatherRequest {
    @NonNull
    private int count;
}
