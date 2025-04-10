
package com.ilnur.WeatherBot.ForecastForGeoPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudsForecastGeo {

    @JsonProperty("all")
    private Integer all;

    public CloudsForecastGeo() {
    }

    public CloudsForecastGeo(Integer all) {
        super();
        this.all = all;
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }
}
