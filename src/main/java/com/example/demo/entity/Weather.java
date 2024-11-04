package com.example.demo.entity;

public class Weather {
    private String weather;
    private String temperature;
    private String recommend;

    public Weather() {}

    public void updateWeather(String weather, String temperature, String recommend) {
        this.weather = weather;
        this.temperature = temperature;
        this.recommend = recommend;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getRecommend() {
        return recommend;
    }
}
