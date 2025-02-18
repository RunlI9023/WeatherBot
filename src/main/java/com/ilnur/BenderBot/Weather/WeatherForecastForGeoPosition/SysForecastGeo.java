
package com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysForecastGeo {

    @JsonProperty("pod")
    private String pod;

    public SysForecastGeo() {
    }

    public SysForecastGeo(String pod) {
        super();
        this.pod = pod;
    }

    public String getPod() {
        return pod;
    }
}
