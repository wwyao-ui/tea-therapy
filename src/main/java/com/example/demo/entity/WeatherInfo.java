package com.example.demo.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class WeatherInfo {
    @SerializedName("status")
    private String status;
    @SerializedName("info")
    private String info;
    @SerializedName("infocode")
    private String infocode;
    @SerializedName("lives")
    private List<LiveWeather> lives;

    public static class LiveWeather {
        @SerializedName("province")
        private String province;
        @SerializedName("city")
        private String city;
        @SerializedName("adcode")
        private String adcode;
        @SerializedName("weather")
        private String weather;
        @SerializedName("temperature")
        private String temperature;
        @SerializedName("winddirection")
        private String winddirection;
        @SerializedName("windpower")
        private String windpower;
        @SerializedName("humidity")
        private String humidity;
        @SerializedName("reporttime")
        private String reporttime;
        @SerializedName("temperature_float")
        private Double temperatureFloat;
        @SerializedName("humidity_float")
        private Double humidityFloat;

        @SerializedName("recommend")
        private String recommend;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getHumidity() {
            return humidity;
        }

        public String getWindpower() {
            return windpower;
        }

        public String getWinddirection() {
            return winddirection;
        }

        public String getReporttime() {
            return reporttime;
        }
    }
}
