package com.ilnur.WeatherBot.ForecastForCityName;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class ListForecastForCityName {

    @JsonProperty("dt")
    private Integer dt;
    @JsonProperty("main")
    private MainForecastForCityName main;
    @JsonProperty("weather")
    private java.util.List<WeatherForecastForCityName> weather;
    @JsonProperty("clouds")
    private CloudsForecastForCityName clouds;
    @JsonProperty("wind")
    private WindForecastForCityName wind;
    @JsonProperty("visibility")
    private Integer visibility;
    @JsonProperty("pop")
    private Integer pop;
    @JsonProperty("sys")
    private SysForecastForCityName sys;
    @JsonProperty("dt_txt")
    private String dtTxt;
    @JsonProperty("snow")
    private SnowForecastForCityName snow;

    public ListForecastForCityName() {
    }

    public ListForecastForCityName(Integer dt, MainForecastForCityName main, java.util.List<WeatherForecastForCityName> weather, CloudsForecastForCityName clouds, WindForecastForCityName wind, Integer visibility, Integer pop, SysForecastForCityName sys, String dtTxt, SnowForecastForCityName snow) {
        super();
        this.dt = dt;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.visibility = visibility;
        this.pop = pop;
        this.sys = sys;
        this.dtTxt = dtTxt;
        this.snow = snow;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public MainForecastForCityName getMain() {
        return main;
    }

    public void setMain(MainForecastForCityName main) {
        this.main = main;
    }

    public java.util.List<WeatherForecastForCityName> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<WeatherForecastForCityName> weather) {
        this.weather = weather;
    }

    public CloudsForecastForCityName getClouds() {
        return clouds;
    }

    public void setClouds(CloudsForecastForCityName clouds) {
        this.clouds = clouds;
    }

    public WindForecastForCityName getWind() {
        return wind;
    }

    public void setWind(WindForecastForCityName wind) {
        this.wind = wind;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public SysForecastForCityName getSys() {
        return sys;
    }

    public void setSys(SysForecastForCityName sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public SnowForecastForCityName getSnow() {
        return snow;
    }

    public void setSnow(SnowForecastForCityName snow) {
        this.snow = snow;
    }
}
