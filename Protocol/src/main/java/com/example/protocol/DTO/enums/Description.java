package com.example.protocol.DTO.enums;
/*
        enum, в котором лежат все возможные описания погоды, которые могут передоваться от сервера клиенту
 */
public enum Description {

    //thunderstorm

    THUNDERSTORM_WITH_LIGHT_RAIN("Thunderstorm with light rain"),
    THUNDERSTORM_WITH_RAIN("Thunderstorm with rain "),
    THUNDERSTORM_WITH_HEAVY_RAIN("Thunderstorm with heavy rain"),
    LIGHT_THUNDERSTORM("Light thunderstorm"),
    THUNDERSTORM("Thunderstorm"),
    HEAVY_THUNDERSTORM("Heavy thunderstorm"),
    RAGGED_THUNDERSTORM("Ragged thunderstorm"),
    THUNDERSTORM_WITH_LIGHT_DRIZZLE("Thunderstorm with light drizzle"),
    THUNDERSTORM_WITH_DRIZZLE("Thunderstorm with drizzle"),
    THUNDERSTORM_WITH_HEAVY_DRIZZLE("Thunderstorm with heavy drizzle"),

    //drizzle

    LIGHT_INTENSITY_DRIZZLE("Light intensity drizzle"),
    DRIZZLE("Drizzle"),
    HEAVY_INTENSITY_DRIZZLE("Heavy intensity drizzle"),
    LIGHT_INTENSITY_DRIZZLE_RAIN("Light intensity drizzle rain"),
    DRIZZLE_RAIN("Drizzle rain"),
    HEAVY_INTENSITY_DRIZZLE_RAIN("Heavy intensity drizzle rain"),
    SHOWER_RAIN_AND_DRIZZLE("Shower rain and drizzle"),
    HEAVY_SHOWER_RAIN_AND_DRIZZLE("Heavy shower rain and drizzle"),
    SHOWER_DRIZZLE("Shower drizzle"),

    //rain

    LIGHT_RAIN("Light rain"),
    MODERATE_RAIN("Moderate rain"),
    HEAVY_INTENSITY_RAIN("Heavy intensity rain"),
    VERY_HEAVY_RAIN("Very heavy rain"),
    EXTREME_RAIN("Extreme rain"),
    FREEZING_RAIN("Freezing rain"),
    LIGHT_INTENSITY_SHOWER_RAIN("Light intensity shower rain"),
    SHOWER_RAIN("Shower rain"),
    HEAVY_INTENSITY_SHOWER_RAIN("Heavy intensity shower rain"),
    RAGGED_SHOWER_RAIN("Ragged shower rain"),

    //snow

    LIGHT_SNOW("Light snow"),
    SNOW("Snow"),
    HEAVY_SNOW("Heavy snow"),
    SLEET("Sleet"),
    LIGHT_SHOWER_SLEET("Light shower sleet"),
    SHOWER_SLEET("Shower sleet"),
    LIGHT_RAIN_AND_SNOW("Light rain and snow"),
    RAIN_AND_SNOW("Rain and snow"),
    LIGHT_SHOWER_SNOW("Light shower snow"),
    SHOWER_SNOW("Shower snow"),
    HEAVY_SHOWER_SNOW("Heavy shower snow"),

    //atmosphere

    MIST("Mist"),
    SMOKE("Smoke"),
    HAZE("Haze"),
    DUST("Dust"),
    FOG("Fog"),
    SAND("Sand"),
    VOLCANIC_ASH("Volcanic ash"),
    SQUALLS("Squall"),
    TORNADO("Tornado"),

    //clear

    CLEAR_SKY("Clear sky"),

    //clouds

    FEW_CLOUDS("Few clouds"),
    SCATTERED_CLOUDS("Scattered clouds"),
    BROKEN_CLOUDS("Broken clouds"),
    OVERCAST_CLOUDS("Overcast clouds");



    private String name;
    private Description(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
