/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 *
 * @author 1
 */

@Configuration
public class BenderBotConfig {
    
    private BenderBot benderBot;
    
    @Bean
    public TelegramBotsApi telegramBotsApi(BenderBot benderBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(benderBot);
        return api;
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
	return builder.build();
	}
}
