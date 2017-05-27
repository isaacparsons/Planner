package com.example.isaacparsons.planner.Weather;

/**
 * Created by isaacparsons on 2017-05-26.
 */

public class Weather {
    private String currentTemp;
    private String currentWeather;

    public Weather(String currentTemp, String currentWeather) {
        this.currentTemp = currentTemp;
        this.currentWeather = currentWeather;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }
}
