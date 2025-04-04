package com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class SysForecastForCityName {

    @JsonProperty("pod")
    private String pod;

    public SysForecastForCityName() {
    }

    public SysForecastForCityName(String pod) {
        super();
        this.pod = pod;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }
}
