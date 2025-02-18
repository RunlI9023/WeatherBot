
package com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExampleForecastGeo {

    @JsonProperty("cod")
    private String cod;
    @JsonProperty("message")
    private Integer message;
    @JsonProperty("cnt")
    private Integer cnt;
    @JsonProperty("list")
    private java.util.List<com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition.ListForecastGeo> list;
    @JsonProperty("city")
    private CityForecastGeo city;

    public ExampleForecastGeo() {
    }

    public ExampleForecastGeo(String cod, Integer message, Integer cnt, java.util.List<com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition.ListForecastGeo> list, CityForecastGeo city) {
        super();
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition.ListForecastGeo> getList() {
        return list;
    }

    public void setList(java.util.List<com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition.ListForecastGeo> list) {
        this.list = list;
    }

    public CityForecastGeo getCity() {
        return city;
    }

    public void setCity(CityForecastGeo city) {
        this.city = city;
    }
}
