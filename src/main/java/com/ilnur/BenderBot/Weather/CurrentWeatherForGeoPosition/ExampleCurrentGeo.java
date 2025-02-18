
package com.ilnur.BenderBot.Weather.CurrentWeatherForGeoPosition;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExampleCurrentGeo {

    @JsonProperty("coord")
    private CoordCurrentGeo coord;
    @JsonProperty("weather")
    private List<WeatherCurrentGeo> weather;
    @JsonProperty("base")
    private String base;
    @JsonProperty("main")
    private MainCurrentGeo main;
    @JsonProperty("visibility")
    private Integer visibility;
    @JsonProperty("wind")
    private WindCurrentGeo wind;
    @JsonProperty("clouds")
    private CloudCurrentGeo clouds;
    @JsonProperty("dt")
    private Integer dt;
    @JsonProperty("sys")
    private SysCurrentGeo sys;
    @JsonProperty("timezone")
    private Integer timezone;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cod")
    private Integer cod;

    public ExampleCurrentGeo() {
    }

    public ExampleCurrentGeo(CoordCurrentGeo coord, List<WeatherCurrentGeo> weather, String base, MainCurrentGeo main, Integer visibility, WindCurrentGeo wind, CloudCurrentGeo clouds, Integer dt, SysCurrentGeo sys, Integer timezone, Integer id, String name, Integer cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public CoordCurrentGeo getCoord() {
        return coord;
    }

    public void setCoord(CoordCurrentGeo coord) {
        this.coord = coord;
    }

    public List<WeatherCurrentGeo> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherCurrentGeo> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }


    public void setBase(String base) {
        this.base = base;
    }

    public MainCurrentGeo getMain() {
        return main;
    }

    public void setMain(MainCurrentGeo main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public WindCurrentGeo getWind() {
        return wind;
    }

    public void setWind(WindCurrentGeo wind) {
        this.wind = wind;
    }

    public CloudCurrentGeo getClouds() {
        return clouds;
    }

    public void setClouds(CloudCurrentGeo clouds) {
        this.clouds = clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public SysCurrentGeo getSys() {
        return sys;
    }

    public void setSys(SysCurrentGeo sys) {
        this.sys = sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
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

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }
    
    public String getDescription() {
        String description = null;
        for (int i = 0; i < getWeather().size(); i++) {
            description = getWeather().get(i).getDescription();
            }
        return description;
        }
}
