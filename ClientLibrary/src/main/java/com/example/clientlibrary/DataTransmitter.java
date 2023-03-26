package com.example.clientlibrary;

import com.example.protocol.DTO.IO.WeatherRequest;
import com.example.protocol.DTO.IO.WeatherResponse;
import com.example.protocol.interfaces.IWeatherService;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

/*
    Класс, устанавливающий общение клиент-сервер по WebSocket
 */
public class DataTransmitter implements IWeatherService {
    /*
        URL, по которому отправлять данные
     */
    private final String URL;
    /*
        Маппинг, по которому придет сообщение
     */
    private static final String SEND_DATA_ENDPOINT = "/app/";
    /*
        Маппинг, по которому уйдут обработанные данные
     */
    private static final String SUBSCRIBE_DATA_ENDPOINT = "/topic/";

    private CompletableFuture<WeatherResponse> completableFuture;

    public DataTransmitter() {
        URL = "ws://localhost:" + 8080 + "/weather";
    }

    public WeatherResponse getData(WeatherRequest weatherRequest)
            throws InterruptedException, ExecutionException, TimeoutException {

        completableFuture = new CompletableFuture<>();
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
        /*
            Подписка на созданные конечные точки и отправка сообщения им
         */
        StompSession stompSession =
                stompClient.connect(URL, new StompSessionHandlerAdapter() {
                }).get(10, SECONDS);

        stompSession.subscribe(
                SUBSCRIBE_DATA_ENDPOINT + weatherRequest.getCity().getName(),
                new CreateGameStompFrameHandler());

        stompSession.send(SEND_DATA_ENDPOINT + weatherRequest.getCity().getName(),
                weatherRequest);
        /*
            Отправка запроса /weather/app/{city} и возвращение клиенту полученного результата
         */

        return completableFuture.get(10, SECONDS);


    }


    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class CreateGameStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return WeatherResponse.class;
        }
        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((WeatherResponse) o);
        }
    }
}




