/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilnur.BenderBot.Rest.BenderBotRestClient;
import com.ilnur.BenderBot.Weather.BenderBotCommonWeather;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author 1
 */
//@Controller
//@RequestMapping("/test")
//public class TestJsonSerialize {
//    
//    ObjectMapper objectMapper = new ObjectMapper();
//    
//    @Autowired
//    private BenderBotRestClient benderBotRestClient;
//    
//    public TestJsonSerialize() {}
//
//    public TestJsonSerialize(BenderBotRestClient benderBotRestClient) throws JsonProcessingException {
//        this.benderBotRestClient = benderBotRestClient;
//    }
//    
//    public Map<String, Object> testMap() throws JsonProcessingException {
//        return objectMapper.readValue(benderBotRestClient.getWeather(), new TypeReference<>(){});
//    }
//    
//}
