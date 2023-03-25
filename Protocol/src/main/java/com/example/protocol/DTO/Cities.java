package com.example.protocol.DTO;

public enum Cities {

    MOSCOW("Moscow"),
    PETERSBURG("Saint Petersburg");

    private String name;
    private Cities(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
