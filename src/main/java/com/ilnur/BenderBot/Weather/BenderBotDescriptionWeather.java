/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

/**
 *
 * @author 1
 */

@Component
public class BenderBotDescriptionWeather {
    
    @JsonProperty("id")
    private long id;
    @JsonProperty("main")
    private String main;
    @JsonProperty("description")
    private String description;
    @JsonProperty("icon")
    private String icon;

    public BenderBotDescriptionWeather() {
        }
    public BenderBotDescriptionWeather(long id, String main, String description, String icon) {
        super();
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
        }

    @JsonProperty("id")
    public long getId() {
    return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
    this.id = id;
    }

    @JsonProperty("main")
    public String getMain() {
    return main;
    }

    @JsonProperty("main")
    public void setMain(String main) {
    this.main = main;
    }

    @JsonProperty("description")
    public String getDescription() {
    return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
    this.description = description;
    }

    @JsonProperty("icon")
    public String getIcon() {
    return icon;
    }

    @JsonProperty("icon")
    public void setIcon(String icon) {
    this.icon = icon;
    }
}
