
package com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityForecastGeo {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("coord")
    private CoordForecastGeo coord;
    @JsonProperty("country")
    private String country;
    @JsonProperty("population")
    private Integer population;
    @JsonProperty("timezone")
    private Integer timezone;
    @JsonProperty("sunrise")
    private Integer sunrise;
    @JsonProperty("sunset")
    private Integer sunset;

    public CityForecastGeo() {
    }

    public CityForecastGeo(Integer id, String name, CoordForecastGeo coord, String country, Integer population, Integer timezone, Integer sunrise, Integer sunset) {
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

    public CoordForecastGeo getCoord() {
        return coord;
    }

    public void setCoord(CoordForecastGeo coord) {
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

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }
}
