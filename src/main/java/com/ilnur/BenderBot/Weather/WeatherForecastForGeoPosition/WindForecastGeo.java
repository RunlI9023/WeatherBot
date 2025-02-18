
package com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class WindForecastGeo {

    @JsonProperty("speed")
    private Double speed;
    @JsonProperty("deg")
    private Integer deg;
    @JsonProperty("gust")
    private Double gust;

    public WindForecastGeo() {
    }

    public WindForecastGeo(Double speed, Integer deg, Double gust) {
        super();
        this.speed = speed;
        this.deg = deg;
        this.gust = gust;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    public Double getGust() {
        return gust;
    }

    public void setGust(Double gust) {
        this.gust = gust;
    }
}
