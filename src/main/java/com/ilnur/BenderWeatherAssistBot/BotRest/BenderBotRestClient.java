package com.ilnur.BenderWeatherAssistBot.BotRest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilnur.BenderWeatherAssistBot.CurrentWeatherForCityName.WeatherNowCurrent;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName.ExampleForecastForCityName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BenderBotRestClient {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${weather.apitoken}")
    private String weatherApiToken;
    @Value("${weather.city_name}")
    private String cityName;
    private Double geoLatitude;
    private Double geoLongitude;
//    WeatherNowCurrent weatherNowCurrent;
//    ExampleForecastForCityName weatherForecastForCityName;
//
//    public WeatherNowCurrent getWeatherNowCurrent() throws JsonProcessingException{
//        WeatherNowCurrent weatherNowCurrent;
//        return weatherNowCurrent = objectMapper.readValue(getCurrentWeatherForCityName(cityName), WeatherNowCurrent.class);
//    }
//    
//    public ExampleForecastForCityName getWeatherForecastForCityName() throws JsonProcessingException{
//        ExampleForecastForCityName weatherForecastForCityName;
//        return weatherForecastForCityName = objectMapper.readValue(getWeatherForecastForCityName(cityName), ExampleForecastForCityName.class);
//    }

    public String getCurrentWeatherForCityName(String cityName) {
        setCityName(cityName);
        return restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?q=" + getCityName() + 
                "&appid=" + getWeatherApiToken() + 
                        "&lang=ru&units=metric", String.class);
    }
    
    public String getWeatherForecastForCityName(String cityName) {
        setCityName(cityName);
        return restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/forecast?q=" + getCityName() + 
                "&appid=" + getWeatherApiToken() + 
                "&lang=ru&units=metric", String.class);
    }
    
    public String getCurrentWeatherForGeoposition(String lat, String lon) {
        return restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + 
                "&lon=" + lon + "&appid=" + getWeatherApiToken() + 
                "&lang=ru&units=metric", String.class);
    }
    
    public String getWeatherForecastForGeoposition(String lat, String lon) {
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
    
    public Double getGeoLatitude() {
        return geoLatitude;
    }

    public void setGeoLatitude(Double geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public Double getGeoLongitude() {
        return geoLongitude;
    }

    public void setGeoLongitude(Double geoLongitude) {
        this.geoLongitude = geoLongitude;
    }
}