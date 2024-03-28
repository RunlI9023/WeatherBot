/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Weather;

/**
 *
 * @author 1
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"temp",
"feels_like",
"temp_min",
"temp_max",
"pressure",
"humidity",
"sea_level",
"grnd_level"
})
@Component
public class BenderBotMainWeather {

    @JsonProperty("temp")
    private double temp;
    @JsonProperty("feels_like")
    private double feelsLike;
    @JsonProperty("temp_min")
    private double tempMin;
    @JsonProperty("temp_max")
    private double tempMax;
    @JsonProperty("pressure")
    private long pressure;
    @JsonProperty("humidity")
    private long humidity;
    @JsonProperty("sea_level")
    private long seaLevel;
    @JsonProperty("grnd_level")
    private long grndLevel;

/**
* No args constructor for use in serialization
*
*/
public BenderBotMainWeather() {
}

public BenderBotMainWeather(
        double temp, double feelsLike, double tempMin, 
        double tempMax, long pressure, long humidity, 
        long seaLevel, long grndLevel) {
    super();
    this.temp = temp;
    this.feelsLike = feelsLike;
    this.tempMin = tempMin;
    this.tempMax = tempMax;
    this.pressure = pressure;
    this.humidity = humidity;
    this.seaLevel = seaLevel;
    this.grndLevel = grndLevel;
}

    @JsonProperty("temp")
    public double getTemp() {
        return temp;
    }

    @JsonProperty("temp")
    public void setTemp(double temp) {
        this.temp = temp;
    }

    @JsonProperty("feels_like")
    public double getFeelsLike() {
        return feelsLike;
    }

    @JsonProperty("feels_like")
    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    @JsonProperty("temp_min")
    public double getTempMin() {
        return tempMin;
    }

    @JsonProperty("temp_min")
    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    @JsonProperty("temp_max")
    public double getTempMax() {
        return tempMax;
    }

    @JsonProperty("temp_max")
    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    @JsonProperty("pressure")
    public long getPressure() {
        return pressure;
    }

    @JsonProperty("pressure")
    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    @JsonProperty("humidity")
    public long getHumidity() {
        return humidity;
    }

    @JsonProperty("humidity")
    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    @JsonProperty("sea_level")
    public long getSeaLevel() {
        return seaLevel;
    }

    @JsonProperty("sea_level")
    public void setSeaLevel(long seaLevel) {
        this.seaLevel = seaLevel;
    }

    @JsonProperty("grnd_level")
    public long getGrndLevel() {
        return grndLevel;
    }

    @JsonProperty("grnd_level")
    public void setGrndLevel(long grndLevel) {
        this.grndLevel = grndLevel;
    }
}