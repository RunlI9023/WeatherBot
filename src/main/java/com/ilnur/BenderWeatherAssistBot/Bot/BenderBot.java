package com.ilnur.BenderWeatherAssistBot.Bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilnur.BenderWeatherAssistBot.BotRest.BenderBotRestClient;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BenderBot extends TelegramLongPollingBot {
    
    @Value("${botApiToken}")
    private String botApiToken;
    @Value("${adminId}")
    private long adminId;
    @Autowired
    private BenderBotRestClient benderBotRestClient;
    @Autowired
    private BenderBotWeatherMessageGenerator benderBotWeatherMessage;
    private long userId;
    private String userName;
    
   
    @Override
    public void onUpdateReceived(Update update) {
            
        if (update.hasMessage() && !update.getMessage().hasLocation() && update.getMessage().getText().equals("/start")) {
            setUserId(update.getMessage().getFrom().getId());
            setUserName(update.getMessage().getFrom().getFirstName());
            System.out.println("Username: " + getUserName() + ", userID: " + getUserId());
                sendMess(benderBotWeatherMessage.greetingUser(getUserId(), getUserName()));
                sendMess(benderBotWeatherMessage.requestGeoPosition(getUserId()));
        }
        else if(update.getMessage().hasLocation()) {
                setUserId(update.getMessage().getFrom().getId());
                benderBotRestClient.setGeoLatitude(update.getMessage().getLocation().getLatitude());
                benderBotRestClient.setGeoLongitude(update.getMessage().getLocation().getLongitude());
                System.out.println(benderBotRestClient.getGeoLatitude() + ", " +  benderBotRestClient.getGeoLongitude());
                try {
                            sendMess(benderBotWeatherMessage.weatherForecastForGeoposition(getUserId()));
                } catch (JsonProcessingException e) {
                    System.out.println("JsonProcessingException: " + e);
                } catch (HttpClientErrorException e) {
                    System.out.println("Город не найден. " + e);
                } catch (ParseException ex) {
                Logger.getLogger(BenderBot.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        else if (!"/start".equals(update.getMessage().getText()) && !update.getMessage().hasLocation()) {
            setUserId(update.getMessage().getFrom().getId());
            String city = update.getMessage().getText();
            try {

                sendMess(benderBotWeatherMessage.weatherForecastForCityName(getUserId(), city));
            } catch (HttpClientErrorException e) {
                sendMess(benderBotWeatherMessage.cityNotFound(getUserId()));
                System.out.println("Город не найден: " + e);
            } catch (JsonProcessingException ex) {
                System.out.println("JsonProcessingException" + ex);
            } catch (ParseException ex) {
                Logger.getLogger(BenderBot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getBotToken() {
        return botApiToken;
    }
    
    @Override
    public String getBotUsername() {
        return "Bender_Bot_Assistant";
    }
    
    public void sendMess(SendMessage message) {
        try {
            execute(message);
        }
        catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
    
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
