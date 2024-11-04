package com.example.demo.common;

import com.example.demo.entity.WeatherInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;

import java.io.IOException;

public class AMapWeatherClient {

    private static final String API_KEY = "999d97c31d24c6f60d8a0a08761402b9";
    private static final String BASE_URL = "https://restapi.amap.com/v3/weather/weatherInfo";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static WeatherInfo getWeatherInfo(String cityCode) throws IOException {
        String url = BASE_URL + "?city=" + cityCode + "&key=" + API_KEY;
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.err.println("Request failed with HTTP error code " + response.code());
            String responseBody = response.body().string();
            System.err.println("Response body: " + responseBody);
            return gson.fromJson(responseBody, WeatherInfo.class);
        }
    }
}

