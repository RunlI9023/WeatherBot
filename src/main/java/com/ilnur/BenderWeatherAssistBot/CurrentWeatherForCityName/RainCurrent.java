
package com.ilnur.BenderWeatherAssistBot.CurrentWeatherForCityName;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RainCurrent {

    @JsonProperty("1h")
    private Float _1h;
    @JsonProperty("3h")
    private Float _3h;

    public RainCurrent() {
    }

    public RainCurrent(Float _1h, Float _3h) {
        super();
        this._1h = _1h;
        this._3h = _3h;
    }

    public Float get1h() {
        return _1h;
    }

    public void set1h(Float _1h) {
        this._1h = _1h;
    }

    public Float get3h() {
        return _3h;
    }

    public void set3h(Float _3h) {
        this._3h = _3h;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(RainCurrent.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("_1h");
        sb.append('=');
        sb.append(((this._1h == null)?"<null>":this._1h));
        sb.append(',');
        sb.append("_3h");
        sb.append('=');
        sb.append(((this._3h == null)?"<null>":this._3h));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
