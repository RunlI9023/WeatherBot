package com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class SnowForecastForCityName {

    @JsonProperty("3h")
    private Double _3h;

    public SnowForecastForCityName() {
    }

    public SnowForecastForCityName(Double _3h) {
        super();
        this._3h = _3h;
    }

    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }
}
