package com.ilnur.BenderWeatherAssistBot.BotRest;

import com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName.WeatherForecastForCityNameMain;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForGeoPosition.WeatherForecastFoGeoposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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

    public WeatherForecastForCityNameMain getWeatherForecastForCityName(String cityName) {
        setCityName(cityName);
        return restClient
        .get()
        .uri("https://api.openweathermap.org/data/2.5/forecast?q={cityName}&appid={weatherApiToken}&lang=ru&units=metric", 
                getCityName(), getWeatherApiToken())
        .accept(APPLICATION_JSON)
        .retrieve()
        .body(WeatherForecastForCityNameMain.class);
    }
    
    public WeatherForecastFoGeoposition getWeatherForecastForGeoposition(String lat, String lon) {
        return restClient
        .get()
        .uri("https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={weatherApiToken}&lang=ru&units=metric", 
                lat, lon, getWeatherApiToken())
        .accept(APPLICATION_JSON)
        .retrieve()
        .body(WeatherForecastFoGeoposition.class);
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