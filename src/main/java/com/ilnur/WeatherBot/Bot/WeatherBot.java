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
import org.apache.logging.log4j.LogManager;

@Component
public class WeatherBot extends TelegramLongPollingBot {
    
    @Value("${botApiToken}")
    private String botApiToken;
    @Value("${botUserName}")
    private String botUserName;
    @Value("${botAdminId}")
    private Long botAdminId;
    private BotUser botUser;
    private final BotRestClient restClient;
    private final MessageGenerator messageGenerator;
    private final BotUserRepository userRepository;
    private static final Logger logger = Logger.getLogger(WeatherBot.class.getName());
    
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
            botUser = new BotUser(update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getId());
            if (userRepository.existsByBotUserId(botUser.getBotUserId())) {
                logger.log(Level.INFO, "User already exist {0}", botUserName);
            }
            else {
                userRepository.save(botUser);
                logger.log(Level.INFO, "Add new user {0}", botUserName);
                sendMess(messageGenerator.forAdmin(botAdminId, botUser.getBotUserName(), botUser.getBotUserId()));
            }
            sendMess(messageGenerator.greetingUser(botUser.getBotUserId(), botUser.getBotUserName()));
            sendMess(messageGenerator.requestGeoPosition(botUser.getBotUserId()));
        }
        else if(update.getMessage().hasLocation()) {
            restClient.setGeoLatitude(update.getMessage().getLocation().getLatitude());
            restClient.setGeoLongitude(update.getMessage().getLocation().getLongitude());
                try {
                    sendMess(messageGenerator.weatherForecastForGeoposition(botUser.getBotUserId()));
                } catch (JsonProcessingException e) {
                    logger.log(Level.INFO, "JSON {0}", e);
                } catch (HttpClientErrorException e) {
                    logger.log(Level.INFO, "HttpClientErrorException {0}", e);
                } catch (ParseException e) {
                    logger.log(Level.INFO, "ParseException {0}", e);
                }
            }
        else if (!"/start".equals(update.getMessage().getText()) && !update.getMessage().hasLocation()) {
            String city = update.getMessage().getText();
            try {
                sendMess(messageGenerator.weatherForecastForCityName(botUser.getBotUserId(), city));
//                for (BotUser user: userRepository.findAll()) {
//                    if (user.getBotUserId().equals(botUser.getBotUserId())) {
//                        user.getBotFindCityList().add(city);
//                        user.getBotFindCityList().stream().forEach(x -> System.out.println(x));
//                    }
//                }
            } catch (HttpClientErrorException e) {
                sendMess(messageGenerator.cityNotFound(botUser.getBotUserId()));
                logger.log(Level.INFO, "JSON {0}", e);
            } catch (JsonProcessingException e) {
                logger.log(Level.INFO, "HttpClientErrorException {0}", e);
            } catch (ParseException e) {
                logger.log(Level.INFO, "ParseException {0}", e);
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
}
