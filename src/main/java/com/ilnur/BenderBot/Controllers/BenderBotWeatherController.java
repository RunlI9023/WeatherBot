/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.*;
import com.ilnur.BenderBot.Weather.BenderBotCommonWeather;
import com.ilnur.BenderBot.Rest.BenderBotRestClient;
import com.ilnur.BenderBot.Weather.BenderBotMainWeather;
import com.ilnur.BenderBot.Weather.BenderBotDescriptionWeather;
import java.util.Arrays;

/**
 * @author ЭмирНурияКарим
 * apitoken 733ddc1d0dfded45e19188b329447d8c
 */

@Controller
@RequestMapping("/weather")
public class BenderBotWeatherController {
    
    @Autowired
    private final BenderBotRestClient benderBotRestClient;
    
    @Autowired
    private BenderBotCommonWeather benderBotCommonWeather;
    
    @Autowired
    private final BenderBotDescriptionWeather benderBotDescriptionWeather;
    
    @Autowired
    private final BenderBotMainWeather benderBotMainWeather;
    
//    @Autowired
//    private final TestJsonSerialize testJsonSerialize;
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    public BenderBotWeatherController(
            BenderBotRestClient benderBotRestClient, 
            BenderBotCommonWeather benderBotCurrentWeather, 
            BenderBotDescriptionWeather benderBotDescriptionWeather,
            BenderBotMainWeather benderBotMainWeather) {
        this.benderBotRestClient = benderBotRestClient;
        this.benderBotCommonWeather = benderBotCurrentWeather;
        this.benderBotDescriptionWeather = benderBotDescriptionWeather;
        this.benderBotMainWeather = benderBotMainWeather;
        //this.testJsonSerialize = testJsonSerialize;
        }
    
    @GetMapping
    public String getWeather(Model model) throws JsonProcessingException {
        benderBotCommonWeather = objectMapper.readValue(
                benderBotRestClient.getWeather(), 
                BenderBotCommonWeather.class);
        model.addAttribute("name", benderBotCommonWeather.getName());
        model.addAttribute("temp", benderBotMainWeather.getTemp());
        model.addAttribute("feels_like", benderBotMainWeather.getFeelsLike());
        model.addAttribute("temp_min", benderBotMainWeather.getTempMin());
        model.addAttribute("temp_max", benderBotMainWeather.getTempMax());
        model.addAttribute("pressure", benderBotMainWeather.getPressure());
        model.addAttribute("humidity", benderBotMainWeather.getHumidity());
        model.addAttribute("sea_level", benderBotMainWeather.getSeaLevel());
        model.addAttribute("grnd_level", benderBotMainWeather.getGrndLevel());
        model.addAttribute("description", benderBotCommonWeather.getWeather());
        model.addAttribute("visibility", benderBotCommonWeather.getVisibility());
        model.addAttribute("weather", benderBotRestClient.getWeather());
        //model.addAttribute("test", testJsonSerialize.testMap().toString());
        return "getweather";
        
    }
    
    
}
