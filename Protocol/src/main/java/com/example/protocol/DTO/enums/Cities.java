package com.example.protocol.DTO.enums;
/*
        enum, в котором лежат все возможные названия городов, которые могут передоваться от клиента серверу
     и от сервера клиенту
 */
public enum Cities {

    MOSCOW("Moscow"),
    PETERSBURG("Petersburg");

    private String name;
    private Cities(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
