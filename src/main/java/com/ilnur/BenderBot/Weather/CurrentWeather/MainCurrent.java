
package com.ilnur.BenderBot.Weather.CurrentWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class MainCurrent {

    @JsonProperty("temp")
    private Float temp;
    @JsonProperty("feels_like")
    private Float feelsLike;
    @JsonProperty("temp_min")
    private Float tempMin;
    @JsonProperty("temp_max")
    private Float tempMax;
    @JsonProperty("pressure")
    private int pressure;
    @JsonProperty("humidity")
    private int humidity;
    @JsonProperty("sea_level")
    private int seaLevel;
    @JsonProperty("grnd_level")
    private int grndLevel;

    public MainCurrent() {
    }

    public MainCurrent(Float temp, Float feelsLike, Float tempMin, Float tempMax, int pressure, int humidity, int seaLevel, int grndLevel) {
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

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Float getTempMin() {
        return tempMin;
    }

    public void setTempMin(Float tempMin) {
        this.tempMin = tempMin;
    }

    public Float getTempMax() {
        return tempMax;
    }

    public void setTempMax(Float tempMax) {
        this.tempMax = tempMax;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public int getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(int grndLevel) {
        this.grndLevel = grndLevel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MainCurrent.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("temp");
        sb.append('=');
        sb.append(((this.temp == null)?"<null>":this.temp));
        sb.append(',');
        sb.append("feelsLike");
        sb.append('=');
        sb.append(((this.feelsLike == null)?"<null>":this.feelsLike));
        sb.append(',');
        sb.append("tempMin");
        sb.append('=');
        sb.append(((this.tempMin == null)?"<null>":this.tempMin));
        sb.append(',');
        sb.append("tempMax");
        sb.append('=');
        sb.append(((this.tempMax == null)?"<null>":this.tempMax));
        sb.append(',');
        sb.append("pressure");
        sb.append('=');
//        sb.append(((this.pressure == null)?"<null>":this.pressure));
//        sb.append(',');
//        sb.append("humidity");
//        sb.append('=');
//        sb.append(((this.humidity == null)?"<null>":this.humidity));
//        sb.append(',');
//        sb.append("seaLevel");
//        sb.append('=');
//        sb.append(((this.seaLevel == null)?"<null>":this.seaLevel));
//        sb.append(',');
//        sb.append("grndLevel");
//        sb.append('=');
//        sb.append(((this.grndLevel == null)?"<null>":this.grndLevel));
//        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
