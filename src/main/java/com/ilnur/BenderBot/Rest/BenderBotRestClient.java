/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Rest;

import java.net.UnknownHostException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * https://catfact.ninja/fact
 * @author ЭмирНурияКарим
 */
//https://api.openweathermap.org/data/2.5/weather?q=Kazan&appid=733ddc1d0dfded45e19188b329447d8c&lang=ru

@Component
public class BenderBotRestClient {
    
    private final RestTemplate restTemplate;

    public BenderBotRestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    public String getWeather() {
        return restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?q=Нурлат&appid=733ddc1d0dfded45e19188b329447d8c&lang=ru", String.class);
    }
}