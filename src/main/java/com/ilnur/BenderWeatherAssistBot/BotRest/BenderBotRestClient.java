package com.ilnur.BenderWeatherAssistBot.BotRest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilnur.BenderWeatherAssistBot.CurrentWeatherForCityName.WeatherNowCurrent;
import com.ilnur.BenderWeatherAssistBot.CurrentWeatherForGeoPosition.ExampleCurrentGeo;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName.ExampleForecastForCityName;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForGeoPosition.ExampleForecastGeo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class BenderBotRestClient {

    private RestClient restClient;
    @Value("${openWeatherMapApiToken}")
    private String weatherApiToken;
    private String cityName;
    private Double geoLatitude;
    private Double geoLongitude;

    public BenderBotRestClient() {
    }

    @Autowired
    public BenderBotRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public WeatherNowCurrent getCurrentWeatherForCityName(String cityName) {
        setCityName(cityName);
        return restClient
        .get()
        .uri("https://api.openweathermap.org/data/2.5/weather?q={cityName}&appid={weatherApiToken}&lang=ru&units=metric", 
                getCityName(), getWeatherApiToken())
        .accept(APPLICATION_JSON)
        .retrieve()
        .body(WeatherNowCurrent.class);
    }
    
    public ExampleForecastForCityName getWeatherForecastForCityName(String cityName) {
        setCityName(cityName);
        return restClient
        .get()
        .uri("https://api.openweathermap.org/data/2.5/forecast?q={cityName}&appid={weatherApiToken}&lang=ru&units=metric", 
                getCityName(), getWeatherApiToken())
        .accept(APPLICATION_JSON)
        .retrieve()
        .body(ExampleForecastForCityName.class);
    }
    
    public ExampleCurrentGeo getCurrentWeatherForGeoposition(String lat, String lon) {
        return restClient
        .get()
        .uri("https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={weatherApiToken}&lang=ru&units=metric", 
                lat, lon, getWeatherApiToken())
        .accept(APPLICATION_JSON)
        .retrieve()
        .body(ExampleCurrentGeo.class);
    }
    
    public ExampleForecastGeo getWeatherForecastForGeoposition(String lat, String lon) {
        return restClient
        .get()
        .uri("https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={weatherApiToken}&lang=ru&units=metric", 
                lat, lon, getWeatherApiToken())
        .accept(APPLICATION_JSON)
        .retrieve()
        .body(ExampleForecastGeo.class);
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