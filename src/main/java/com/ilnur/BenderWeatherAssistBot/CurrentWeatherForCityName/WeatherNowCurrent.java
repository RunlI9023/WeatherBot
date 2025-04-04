package com.ilnur.BenderWeatherAssistBot.CurrentWeatherForCityName;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherNowCurrent {

    @JsonProperty("coord")
    private CoordCurrent coord;
    @JsonProperty("visibility")
    private int visibility;
    @JsonProperty("wind")
    private WindCurrent wind;
    @JsonProperty("rain")
    private RainCurrent rain;
    @JsonProperty("clouds")
    private CloudsCurrent clouds;
    @JsonProperty("snow")
    private SnowCurrent snow;
    @JsonProperty("main")
    private MainCurrent main;
    @JsonProperty("base")
    private String base;
    @JsonProperty("name")
    private String name;
    @JsonProperty("dt")
    private int dt;
    @JsonProperty("sys")
    private SysCurrent sys;
    @JsonProperty("timezone")
    private int timezone;
    @JsonProperty("id")
    private int id;
    @JsonProperty("cod")
    private int cod;
    @JsonProperty("weather")
    private List<WeatherCurrent> weather;   
    
    public WeatherNowCurrent() {}

    public WeatherNowCurrent(CoordCurrent coord, int visibility, WindCurrent wind, RainCurrent rain, CloudsCurrent clouds, SnowCurrent snow, MainCurrent main, String base, String name, int dt, SysCurrent sys, int timezone, int id, int cod, List<WeatherCurrent> weather) {
        this.coord = coord;
        this.visibility = visibility;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
        this.snow = snow;
        this.main = main;
        this.base = base;
        this.name = name;
        this.dt = dt;
        this.sys = sys;
        this.timezone = timezone;
        this.id = id;
        this.cod = cod;
        this.weather = weather;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public CoordCurrent getCoord() {
        return coord;
    }

    public void setCoord(CoordCurrent coord) {
        this.coord = coord;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public WindCurrent getWind() {
        return wind;
    }

    public void setWind(WindCurrent wind) {
        this.wind = wind;
    }

    public RainCurrent getRain() {
        return rain;
    }

    public void setRain(RainCurrent rain) {
        this.rain = rain;
    }

    public CloudsCurrent getClouds() {
        return clouds;
    }

    public void setClouds(CloudsCurrent clouds) {
        this.clouds = clouds;
    }

    public SnowCurrent getSnow() {
        return snow;
    }

    public void setSnow(SnowCurrent snow) {
        this.snow = snow;
    }

    public MainCurrent getMain() {
        return main;
    }

    public void setMain(MainCurrent main) {
        this.main = main;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public SysCurrent getSys() {
        return sys;
    }

    public void setSys(SysCurrent sys) {
        this.sys = sys;
    }


    public List<WeatherCurrent> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherCurrent> weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        String description = null;
        for (int i = 0; i < getWeather().size(); i++) {
            description = getWeather().get(i).getDescription();
            }
        return description;
        }
    
    }



