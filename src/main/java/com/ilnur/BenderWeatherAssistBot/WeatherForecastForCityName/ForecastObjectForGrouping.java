package com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ForecastObjectForGrouping {
    
    private String Date;
    //private String dayOfWeek;
    private String Description;
    private Double TempMaximum;
    private Double TempMinimum;
    private Double TempFeelsLike;
    private Integer Humidity;
    private Integer Pressure;
    private Integer Visibility;
    private Integer Clouds;
    private Double WindSpeed;

    public ForecastObjectForGrouping() {
    }

    public ForecastObjectForGrouping(String Date, String Description, Double TempMaximum, Double TempMinimum, Double TempFeelsLike, Integer Humidity, Integer Pressure, Integer Visibility, Integer Clouds, Double WindSpeed) {
        this.Date = Date;
        this.Description = Description;
        this.TempMaximum = TempMaximum;
        this.TempMinimum = TempMinimum;
        this.TempFeelsLike = TempFeelsLike;
        this.Humidity = Humidity;
        this.Pressure = Pressure;
        this.Visibility = Visibility;
        this.Clouds = Clouds;
        this.WindSpeed = WindSpeed;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Double getTempMaximum() {
        return TempMaximum;
    }

    public void setTempMaximum(Double TempMaximum) {
        this.TempMaximum = TempMaximum;
    }

    public Double getTempMinimum() {
        return TempMinimum;
    }

    public void setTempMinimum(Double TempMinimum) {
        this.TempMinimum = TempMinimum;
    }

    public Double getTempFeelsLike() {
        return TempFeelsLike;
    }

    public void setTempFeelsLike(Double TempFeelsLike) {
        this.TempFeelsLike = TempFeelsLike;
    }

    public Integer getHumidity() {
        return Humidity;
    }

    public void setHumidity(Integer Humidity) {
        this.Humidity = Humidity;
    }

    public Integer getPressure() {
        return Pressure;
    }

    public void setPressure(Integer Pressure) {
        this.Pressure = Pressure;
    }

    public Integer getVisibility() {
        return Visibility;
    }

    public void setVisibility(Integer Visibility) {
        this.Visibility = Visibility;
    }

    public Integer getClouds() {
        return Clouds;
    }

    public void setClouds(Integer Clouds) {
        this.Clouds = Clouds;
    }

    public Double getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(Double WindSpeed) {
        this.WindSpeed = WindSpeed;
    }

    
    
}
