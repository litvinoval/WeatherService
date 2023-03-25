package com.example.clientlibrary;

import com.example.protocol.DTO.WeatherRequest;
import com.example.protocol.DTO.WeatherResponse;
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


public class DataTransmitter {
    private String URL;

    private static final String SEND_DATA_ENDPOINT = "/app/";
    private static final String SUBSCRIBE_DATA_ENDPOINT = "/topic/";

    private CompletableFuture<WeatherResponse> completableFuture;

    public DataTransmitter() {
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + 8080 + "/weather";
    }

    public WeatherResponse getData()
            throws InterruptedException, ExecutionException, TimeoutException {
        String city = "Moscow";

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
                new WeatherRequest(1));

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




