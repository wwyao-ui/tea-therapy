package com.example.demo.controller;

import com.example.demo.common.AMapWeatherClient;
import com.example.demo.common.ResultUtil;
import com.example.demo.common.TeaMatcher;
import com.example.demo.entity.WeatherInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/weather")
@RestController
public class WeatherController {
    private static final String CITY_CODE_YUELU = "430104"; // 长沙市岳麓区的城市编码
    @RequestMapping("tea_weather")
    @ResponseBody
    public ResultUtil recommendTeaForYuelu() {
        try {
            WeatherInfo weatherInfo = AMapWeatherClient.getWeatherInfo(CITY_CODE_YUELU);
            if (weatherInfo != null && weatherInfo.getLives() != null && !weatherInfo.getLives().isEmpty()) {
                String temperatureStr = weatherInfo.getLives().get(0).getTemperature();
                String weather = weatherInfo.getLives().get(0).getWeather();
                String humidity = weatherInfo.getLives().get(0).getHumidity();
                String windpower = weatherInfo.getLives().get(0).getWindpower();
                String winddirection = weatherInfo.getLives().get(0).getWinddirection();
                String reporttime = weatherInfo.getLives().get(0).getReporttime();
                String recommend = TeaMatcher.matchTea(Integer.parseInt(temperatureStr));
                if (temperatureStr != null) {
                    int temperature = Integer.parseInt(temperatureStr);
                    int humidityFloat = Integer.parseInt(humidity);
                    String message = "温馨提示，今天长沙岳麓区的天气:" + weather + "  温度:" + temperature + "°C" + "  湿度:" + humidityFloat + "  风力:" + windpower + "  风向:" + winddirection + "  预报发布时间:" + reporttime + "，推荐您喝: " + recommend;
                    return ResultUtil.ok(recommend, weatherInfo);
                }
            }
            return ResultUtil.error("Error getting weather information.");
        } catch (Exception e) {
            return ResultUtil.error("Error");
        }
    }
}