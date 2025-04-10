package com.ilnur.WeatherBot.Bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilnur.WeatherBot.BotRest.BotRestClient;
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
import com.ilnur.WeatherBot.BotRepository.BotUserRepository;

@Component
public class WeatherBot extends TelegramLongPollingBot {
    
    @Value("${botApiToken}")
    private String botApiToken;
    @Value("${botUserName}")
    private String botUserName;
    @Value("${botAdminId}")
    private long botAdminId;
    private long botUserId;
    private String userName;
    private BotUser botUser;
    private BotRestClient restClient;
    private MessageGenerator messageGenerator;
    private BotUserRepository userRepository;

    public WeatherBot() {
    }
    
    @Autowired
    public WeatherBot(BotRestClient restClient, MessageGenerator messageGenerator, BotUserRepository userRepository, BotUser botUser) {
        this.restClient = restClient;
        this.messageGenerator = messageGenerator;
        this.userRepository = userRepository;
        this.botUser = botUser;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && !update.getMessage().hasLocation() && update.getMessage().getText().equals("/start")) {
            setBotUserId(update.getMessage().getFrom().getId());
            setUserName(update.getMessage().getFrom().getFirstName());
                sendMess(messageGenerator.greetingUser(getBotUserId(), getUserName()));
                sendMess(messageGenerator.requestGeoPosition(getBotUserId()));
                for (BotUser user: userRepository.findAll()) {
                    //&& !update.getMessage().getFrom().getId().equals(botAdminId)
                    if (!user.getBotUserId().equals(botUserId) || userRepository.count() ==0 ) {
                        userRepository.save(new BotUser(getUserName(), getBotUserId()));
                        sendMess(messageGenerator.forAdmin(botAdminId, getUserName(), getBotUserId()));
                    }
                }
        }
        else if(update.getMessage().hasLocation()) {
                setBotUserId(update.getMessage().getFrom().getId());
                restClient.setGeoLatitude(update.getMessage().getLocation().getLatitude());
                restClient.setGeoLongitude(update.getMessage().getLocation().getLongitude());
                try {
                    sendMess(messageGenerator.weatherForecastForGeoposition(getBotUserId()));
                } catch (JsonProcessingException e) {
                    Logger.getLogger(WeatherBot.class.getName()).log(Level.WARNING, e.toString());
                } catch (HttpClientErrorException e) {
                    Logger.getLogger(WeatherBot.class.getName()).log(Level.INFO, e.toString());
                } catch (ParseException e) {
                    Logger.getLogger(WeatherBot.class.getName()).log(Level.WARNING, e.toString());
                }
            }
        else if (!"/start".equals(update.getMessage().getText()) && !update.getMessage().hasLocation()) {
            setBotUserId(update.getMessage().getFrom().getId());
            String city = update.getMessage().getText();
            
            try {
                sendMess(messageGenerator.weatherForecastForCityName(getBotUserId(), city));

//                for (BotUser user: userRepository.findAll()) {
//                    if (user.getBotUserId().equals(botUser.getBotUserId())) {
//                        user.getBotFindCityList().add(city);
//                        user.getBotFindCityList().stream().forEach(x -> System.out.println(x));
//                    }
//                }
                
            } catch (HttpClientErrorException e) {
                sendMess(messageGenerator.cityNotFound(getBotUserId()));
                Logger.getLogger(WeatherBot.class.getName()).log(Level.INFO, e.toString());
            } catch (JsonProcessingException e) {
                Logger.getLogger(WeatherBot.class.getName()).log(Level.WARNING, e.toString());
            } catch (ParseException e) {
                Logger.getLogger(WeatherBot.class.getName()).log(Level.WARNING, e.toString());
            }
        }
    }

    public void sendMess(SendMessage message) {
        try {
            execute(message);
        }
        catch (TelegramApiException e){
            Logger.getLogger(WeatherBot.class.getName()).log(Level.WARNING, e.toString());
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
