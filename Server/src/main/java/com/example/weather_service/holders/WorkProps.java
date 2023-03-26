package com.example.weather_service.holders;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/*
    Класс, с помощью которого настраиваются параметры сервера, получаемые из
    файла конфигурации
 */
@ConfigurationProperties(prefix = "working")
@Data
@Validated
public class WorkProps {
    /*
        Установка режима работы сервера
     */
    @Min(value = 0, message = "must be between 0 and 1")
    @Max(value = 1, message = "must be between 0 and 1")
    private int mode = 1;

    /*
        Установка ключа для API сервиса погоды
     */
    private String key = "f98cde64c8f4f20f63f0d2a91c5bfca5";
}
