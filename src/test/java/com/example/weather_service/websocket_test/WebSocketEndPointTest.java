package com.example.weather_service.websocket_test;


import com.example.weather_service.pojo.server_client.WeatherCondition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertNotNull;

/*
    endpoints для тестирования:
    /weather/app/{city}
    /weather/topic/{city}
    Приложение запустится и сконфигурируется на случайном порту
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketEndPointTest {


    /*
        URL, по которому отправлять данные
     */
    private String URL;
    /*
        Маппинг, по которому придет сообщение
     */
    private static final String SEND_DATA_ENDPOINT = "/app/";
    /*
        Маппинг, по которому уйдут обработанные данные
     */
    private static final String SUBSCRIBE_DATA_ENDPOINT = "/topic/";

    private CompletableFuture<WeatherCondition> completableFuture;

    @Before
    public void setup() {
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + 8080 + "/weather";
    }


    @Test
    public void testGetMoscowWeather()
            throws InterruptedException, ExecutionException, TimeoutException {
        String city = "Moscow";
        /*
            Создание Websocket Client для коммуникации с Websocket server.
            Чтобы это сделать используется WebSocketStompClient
         */
        WebSocketStompClient stompClient =
                new WebSocketStompClient(new SockJsClient(createTransportClient()));

        /*
            Добавление JacksonMessageConverter
            Подключение к существующему URL
         */
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession =
                stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        /*
            Подписка на созданные конечные точки и отправка сообщения им
         */
        stompSession.subscribe(
                SUBSCRIBE_DATA_ENDPOINT + city,
                new CreateGameStompFrameHandler());

        stompSession.send(SEND_DATA_ENDPOINT + city,
                            new WeatherCondition(1));

        /*
            Отправка запроса /weather/app/{city}
         */
        WeatherCondition weatherCondition = completableFuture.get(10, SECONDS);

        /*
            Проверки:
            1. Что тип возвращаемого значения не null
            2. Номер, встроенный в ответ, совпадает с номером, которым
             тестовое приложение-клиент помечает запрос
            3. Вернулось настоящее значение температура
            4. Вернулось настоящее состояние погоды
            5. Вернулась погода для запрашиваемого города
         */
        assertNotNull(weatherCondition);
        Assert.assertEquals(
                1, weatherCondition.getCount());
        assertNotNull(
                weatherCondition.getTemp());
        assertNotNull(
                weatherCondition.getPrecipitation());
        Assert.assertEquals(
                "Moscow", weatherCondition.getCity());
    }

    @Test
    public void testGetPeterWeather()
            throws InterruptedException, ExecutionException, TimeoutException {

        /*
            Выполняется как тест выше, только запрос погоды из Санкт-Петербурга
         */
        String city = "Petersburg";

        WebSocketStompClient stompClient =
                new WebSocketStompClient(new SockJsClient(createTransportClient()));

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession =
                stompClient.connect(URL, new StompSessionHandlerAdapter() {
                }).get(1, SECONDS);

        stompSession.subscribe(
                SUBSCRIBE_DATA_ENDPOINT + city,
                new CreateGameStompFrameHandler());

        stompSession.send(SEND_DATA_ENDPOINT + city,
                            new WeatherCondition(1));

        WeatherCondition weatherCondition = completableFuture.get(10, SECONDS);

        assertNotNull(weatherCondition);
        Assert.assertEquals(
                1, weatherCondition.getCount());
        assertNotNull(
                weatherCondition.getTemp());
        assertNotNull(
                weatherCondition.getPrecipitation());
        Assert.assertEquals(
                "Petersburg", weatherCondition.getCity());
    }



    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class CreateGameStompFrameHandler implements StompFrameHandler {

        /*
            Описание типа контента, из которого получаем StompHeaders
         */
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return WeatherCondition.class;
        }
        /*
            Преобразование данных, полученных из StompHeaders в объект
            ожидаемого типа
         */
        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((WeatherCondition) o);
        }
    }
}