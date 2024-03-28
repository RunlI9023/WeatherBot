/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Weather;

import com.ilnur.BenderBot.Weather.BenderBotDescriptionWeather;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author 1
 */

@Component
public class BenderBotCommonWeather {
    
    @JsonIgnore
    private float coord;
    
    @JsonProperty("weather")
    //@JsonIgnore
    private List<BenderBotDescriptionWeather> weather;

    @JsonIgnore
    private String base;
    
    @JsonProperty("main")
    public BenderBotMainWeather benderBotMainWeather;
    
    @JsonProperty("visibility")
    private float visibility;
    
    @JsonIgnore
    private Map<String, Long> wind;
    
    @JsonIgnore
    private Map<String, Long> clouds;
    
    @JsonIgnore
    private Map<String, Long> rain;
    
    @JsonIgnore
    private Map<String, Long> snow;
    
    @JsonIgnore
    private Long dt;
    
    @JsonIgnore
    private Map<String, String> sys;

    @JsonIgnore
    private int timezone;
    
    @JsonIgnore
    private int id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonIgnore
    private int cod;

    public BenderBotCommonWeather(float coord, List<BenderBotDescriptionWeather> weather, String base, BenderBotMainWeather benderBotMinWeather, float visibility, Map<String, Long> wind, Map<String, Long> clouds, Map<String, Long> rain, Map<String, Long> snow, Long dt, Map<String, String> sys, int timezone, int id, String name, int cod) {
        this.coord = coord;
        this.weather = (List<BenderBotDescriptionWeather>) weather;
        this.base = base;
        this.benderBotMainWeather = benderBotMainWeather;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.snow = snow;
        this.dt = dt;
        this.sys = sys;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public BenderBotCommonWeather() {
    }

    public float getCoord() {
        return coord;
    }

    public void setCoord(float coord) {
        this.coord = coord;
    }
    
    @JsonGetter("weather")
    public List<BenderBotDescriptionWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<BenderBotDescriptionWeather> weather) {
        this.weather = (List<BenderBotDescriptionWeather>) weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
    
    @JsonGetter("main")
    public BenderBotMainWeather getMain() {
        return benderBotMainWeather;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public Map<String, Long> getWind() {
        return wind;
    }

    public void setWind(Map<String, Long> wind) {
        this.wind = wind;
    }
    
    public Map<String, Long> getSnow() {
        return snow;
    }

    public void setSnow(Map<String, Long> snow) {
        this.snow = snow;
    }

    public Map<String, Long> getRain() {
        return rain;
    }

    public void setRain(Map<String, Long> rain) {
        this.rain = rain;
    }

    public Map<String, Long> getClouds() {
        return clouds;
    }

    public void setClouds(Map<String, Long> clouds) {
        this.clouds = clouds;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Map<String, String> getSys() {
        return sys;
    }

    public void setSys(Map<String, String> sys) {
        this.sys = sys;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
    
    public void weatherTest() {
        for (int i = 0; i < getWeather().size(); i++) {
            System.out.println("Test: " + getWeather().get(i).toString());
        }
    }
}    

