
package com.ilnur.BenderBot.Weather.CurrentWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class CloudsCurrent {

    @JsonProperty("all")
    private Long all;

    public CloudsCurrent() {
    }

    public CloudsCurrent(Long all) {
        super();
        this.all = all;
    }

    public Long getAll() {
        return all;
    }

    public void setAll(Long all) {
        this.all = all;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CloudsCurrent.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("all");
        sb.append('=');
        sb.append(((this.all == null)?"<null>":this.all));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
