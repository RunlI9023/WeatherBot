package com.ilnur.BenderWeatherAssistBot.Bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilnur.BenderWeatherAssistBot.BotRepository.BenderBotUserRepository;
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
    @Value("${botUserName}")
    private String botUserName;
    @Value("${botAdminId}")
    private long botAdminId;
    private long botUserId;
    private String userName;
    private BenderBotRestClient benderBotRestClient;
    private BenderBotWeatherMessageGenerator benderBotWeatherMessageGenerator;
    private BenderBotUserRepository benderBotUserRepository;

    public BenderBot() {
    }
    
    @Autowired
    public BenderBot(BenderBotRestClient benderBotRestClient, BenderBotWeatherMessageGenerator benderBotWeatherMessageGenerator, BenderBotUserRepository benderBotUserRepository) {
        this.benderBotRestClient = benderBotRestClient;
        this.benderBotWeatherMessageGenerator = benderBotWeatherMessageGenerator;
        this.benderBotUserRepository = benderBotUserRepository;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && !update.getMessage().hasLocation() && update.getMessage().getText().equals("/start")) {
            benderBotUserRepository.save(new BenderBotUser(getUserName(), getBotUserId()));
            setBotUserId(update.getMessage().getFrom().getId());
            setUserName(update.getMessage().getFrom().getFirstName());
                sendMess(benderBotWeatherMessageGenerator.greetingUser(getBotUserId(), getUserName()));
                sendMess(benderBotWeatherMessageGenerator.requestGeoPosition(getBotUserId()));
                if (!update.getMessage().getFrom().getId().equals(botAdminId)) {
                    sendMess(benderBotWeatherMessageGenerator.forAdmin(botAdminId, getUserName(), getBotUserId()));
                }
                
        }
        else if(update.getMessage().hasLocation()) {
                setBotUserId(update.getMessage().getFrom().getId());
                benderBotRestClient.setGeoLatitude(update.getMessage().getLocation().getLatitude());
                benderBotRestClient.setGeoLongitude(update.getMessage().getLocation().getLongitude());
                try {
                    sendMess(benderBotWeatherMessageGenerator.weatherForecastForGeoposition(getBotUserId()));
                } catch (JsonProcessingException e) {
                    Logger.getLogger(BenderBot.class.getName()).log(Level.WARNING, e.toString());
                } catch (HttpClientErrorException e) {
                    Logger.getLogger(BenderBot.class.getName()).log(Level.INFO, e.toString());
                } catch (ParseException e) {
                    Logger.getLogger(BenderBot.class.getName()).log(Level.WARNING, e.toString());
                }
            }
        else if (!"/start".equals(update.getMessage().getText()) && !update.getMessage().hasLocation()) {
            setBotUserId(update.getMessage().getFrom().getId());
            String city = update.getMessage().getText();
            try {
                sendMess(benderBotWeatherMessageGenerator.weatherForecastForCityName(getBotUserId(), city));
                //if (getBotUserId() != benderBotUserRepository.)
                
            } catch (HttpClientErrorException e) {
                sendMess(benderBotWeatherMessageGenerator.cityNotFound(getBotUserId()));
                Logger.getLogger(BenderBot.class.getName()).log(Level.INFO, e.toString());
            } catch (JsonProcessingException e) {
                Logger.getLogger(BenderBot.class.getName()).log(Level.WARNING, e.toString());
            } catch (ParseException e) {
                Logger.getLogger(BenderBot.class.getName()).log(Level.WARNING, e.toString());
            }
        }
    }

    public void sendMess(SendMessage message) {
        try {
            execute(message);
        }
        catch (TelegramApiException e){
            Logger.getLogger(BenderBot.class.getName()).log(Level.WARNING, e.toString());
        }
    }
    
    @Override
    public String getBotToken() {
        return botApiToken;
    }
    
    @Override
    public String getBotUsername() {
        return botUserName;
    }
    
    public long getBotUserId() {
        return botUserId;
    }

    public void setBotUserId(long userId) {
        this.botUserId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
