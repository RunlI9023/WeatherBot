/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * https://catfact.ninja/fact
 * @author ЭмирНурияКарим
 */

@Component
public class BenderBotRestClient {
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.apitoken}")
    private String weatherApiToken;
    
    private String cityName;

    public String getCurrentWeather(String cityName) {
        setCityName(cityName);
        return restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?q=" + getCityName() + 
                "&appid=" + getWeatherApiToken() + 
                "&lang=ru&units=metric", String.class);
    }
    
    public String getGeoWeather(String lat, String lon) {
        return restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + 
                "&lon=" + lon + "&appid=" + getWeatherApiToken() + 
                "&lang=ru&units=metric", String.class);
    }
    
    public String getGeoWeatherForecast(String lat, String lon) {
        return restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + 
                "&lon=" + lon + "&appid=" + getWeatherApiToken() + 
                "&lang=ru&units=metric", String.class);
    }
    
    public String getWeatherApiToken() {
        return weatherApiToken;
    }

    public void setWeatherApiToken(String weatherApiToken) {
        this.weatherApiToken = weatherApiToken;
    }
    
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}