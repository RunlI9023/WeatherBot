package com.ilnur.WeatherBot.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@EnableAsync
public class BotConfiguration implements AsyncConfigurer {
    
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("findWeather");
        executor.initialize();
        return executor;
    }
    
    @Bean
    public TelegramBotsApi telegramBotsApi(WeatherBot benderBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(benderBot);
            return api;
        }
    
    @Bean
    public InlineKeyboardMarkup geoLocationInlineKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Geoposition weather");
        rowInline1.add(inlineKeyboardButton1);
        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);
            return markupInline;
        }
    
    @Bean
    public RestClient restClient() {
        RestClient restClient = RestClient.create();
        return restClient;
    }
}
