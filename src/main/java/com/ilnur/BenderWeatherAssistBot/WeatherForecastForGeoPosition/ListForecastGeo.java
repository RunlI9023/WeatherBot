package com.ilnur.BenderWeatherAssistBot.WeatherForecastForGeoPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListForecastGeo {

    @JsonProperty("dt")
    private Integer dt;
    @JsonProperty("main")
    private MainForecastGeo main;
    @JsonProperty("weather")
    private java.util.List<WeatherForecastGeo> weather;
    @JsonProperty("clouds")
    private CloudsForecastGeo clouds;
    @JsonProperty("wind")
    private WindForecastGeo wind;
    @JsonProperty("visibility")
    private Integer visibility;
    @JsonProperty("pop")
    private Integer pop;
    @JsonProperty("sys")
    private SysForecastGeo sys;
    @JsonProperty("dt_txt")
    private String dtTxt;

    public ListForecastGeo() {
    }

    public ListForecastGeo(Integer dt, MainForecastGeo main, java.util.List<WeatherForecastGeo> weather, CloudsForecastGeo clouds, WindForecastGeo wind, Integer visibility, Integer pop, SysForecastGeo sys, String dtTxt) {
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
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public MainForecastGeo getMain() {
        return main;
    }

    public void setMain(MainForecastGeo main) {
        this.main = main;
    }

    public java.util.List<WeatherForecastGeo> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<WeatherForecastGeo> weather) {
        this.weather = weather;
    }

    public CloudsForecastGeo getClouds() {
        return clouds;
    }

    public void setClouds(CloudsForecastGeo clouds) {
        this.clouds = clouds;
    }

    public WindForecastGeo getWind() {
        return wind;
    }

    public void setWind(WindForecastGeo wind) {
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

    public SysForecastGeo getSys() {
        return sys;
    }

    public void setSys(SysForecastGeo sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
