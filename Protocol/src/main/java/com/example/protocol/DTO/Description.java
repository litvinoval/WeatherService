package com.example.protocol.DTO;

public enum Description {
    THUNDERSTORM("Thunderstorm"),
    DRIZZLE("Drizzle"),
    RAIN("Rain"),
    SNOW("Snow"),
    MIST("Mist"),
    SMOKE("Smoke"),
    HAZE("Haze"),
    DUST("Dust"),
    FOG("Fog"),
    SAND("Sand"),
    ASH("Ash"),
    SQUALL("Squall"),
    TORNADO("Tornado"),
    CLEAR("Clear"),
    CLOUDS("Clouds");

    private String name;
    private Description(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
