package com.ilnur.WeatherBot.RestClient;

import com.ilnur.WeatherBot.ForecastForCityName.ForecastForCityName;
import com.ilnur.WeatherBot.ForecastForGeoPosition.ForecastForGeoposition;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BotRestClient {

    private RestClient restClient;
    @Value("${openWeatherMapApiToken}")
    private String weatherApiToken;
    private String cityName;
    private Double geoLatitude;
    private Double geoLongitude;
    private static final Logger logger = Logger.getLogger(BotRestClient.class.getName());

    public BotRestClient() {
    }

    @Autowired
    public BotRestClient(RestClient restClient) {
        this.restClient = restClient;
    }
    
    @Async
    public CompletableFuture<ForecastForCityName> getWeatherForecastForCityName(String cityName) {
        setCityName(cityName);
        ForecastForCityName forecastForCityName = restClient
        .get()
        .uri("https://api.openweathermap.org/data/2.5/forecast?q={cityName}&appid={weatherApiToken}&lang=ru&units=metric", 
                getCityName(), getWeatherApiToken())
        .accept(APPLICATION_JSON)
        .retrieve()
        .body(ForecastForCityName.class);
        Thread currentThread = Thread.currentThread();
        return CompletableFuture.completedFuture(forecastForCityName);
    }
    
    @Async
    public CompletableFuture<ForecastForGeoposition> getWeatherForecastForGeoposition(String lat, String lon) {
        ForecastForGeoposition forecastForGeoposition = restClient
        .get()
        .uri("https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={weatherApiToken}&lang=ru&units=metric", 
                lat, lon, getWeatherApiToken())
        .accept(APPLICATION_JSON)
        .retrieve()
        .body(ForecastForGeoposition.class);
        Thread currentThread = Thread.currentThread();
        return CompletableFuture.completedFuture(forecastForGeoposition);
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