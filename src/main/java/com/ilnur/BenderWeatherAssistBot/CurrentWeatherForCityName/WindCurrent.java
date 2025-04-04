
package com.ilnur.BenderWeatherAssistBot.CurrentWeatherForCityName;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class WindCurrent {

    @JsonProperty("speed")
    private Float speed;
    @JsonProperty("deg")
    private Long deg;
    @JsonProperty("gust")
    private Float gust;

    public WindCurrent() {
    }

    public WindCurrent(Float speed, Long deg, Float gust) {
        super();
        this.speed = speed;
        this.deg = deg;
        this.gust = gust;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Long getDeg() {
        return deg;
    }

    public void setDeg(Long deg) {
        this.deg = deg;
    }

    public Float getGust() {
        return gust;
    }

    public void setGust(Float gust) {
        this.gust = gust;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(WindCurrent.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("speed");
        sb.append('=');
        sb.append(((this.speed == null)?"<null>":this.speed));
        sb.append(',');
        sb.append("deg");
        sb.append('=');
        sb.append(((this.deg == null)?"<null>":this.deg));
        sb.append(',');
        sb.append("gust");
        sb.append('=');
        sb.append(((this.gust == null)?"<null>":this.gust));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
