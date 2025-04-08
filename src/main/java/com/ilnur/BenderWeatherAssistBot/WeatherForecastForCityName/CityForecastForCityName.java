package com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class CityForecastForCityName {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("coord")
    private CoordForecastForCityName coord;
    @JsonProperty("country")
    private String country;
    @JsonProperty("population")
    private Integer population;
    @JsonProperty("timezone")
    private Integer timezone;
    @JsonProperty("sunrise")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date sunrise;
    @JsonProperty("sunset")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date sunset;

    public CityForecastForCityName() {
    }

    public CityForecastForCityName(Integer id, String name, CoordForecastForCityName coord, String country, Integer population, Integer timezone, Date sunrise, Date sunset) {
        super();
        this.id = id;
        this.name = name;
        this.coord = coord;
        this.country = country;
        this.population = population;
        this.timezone = timezone;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordForecastForCityName getCoord() {
        return coord;
    }

    public void setCoord(CoordForecastForCityName coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }
}
