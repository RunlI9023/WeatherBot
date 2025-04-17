package com.ilnur.WeatherBot.Bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilnur.WeatherBot.BotRepository.BotUserFindCityRepository;
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
import java.util.concurrent.ExecutionException;

@Component
public class WeatherBot extends TelegramLongPollingBot {
    
    @Value("${botApiToken}")
    private String botApiToken;
    @Value("${botUserName}")
    private String botUserName;
    @Value("${botAdminId}")
    private Long botAdminId;
    private BotUser botUser;
    private BotUserFindCity botUserFindCity;
    private final BotRestClient restClient;
    private final MessageGenerator messageGenerator;
    private final BotUserRepository userRepository;
    private final BotUserFindCityRepository userFindCityRepository;
    private static final Logger logger = Logger.getLogger(WeatherBot.class.getName());
    
    @Autowired
    public WeatherBot(BotRestClient restClient, MessageGenerator messageGenerator, BotUserRepository userRepository, BotUserFindCityRepository userFindCityRepository, BotUser botUser) {
        this.restClient = restClient;
        this.messageGenerator = messageGenerator;
        this.userRepository = userRepository;
        this.userFindCityRepository = userFindCityRepository;
        this.botUser = botUser;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && !update.getMessage().hasLocation() && update.getMessage().getText().equals("/start")) {
            if (userRepository.existsByBotUserId(botUser.getBotUserId())) {
                botUser.setBotUserId(update.getMessage().getFrom().getId());
                botUser.setBotUserName(update.getMessage().getFrom().getFirstName());
                logger.log(Level.INFO, "Существующий пользователь");
            }
            else {
                botUser = new BotUser(update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getId());
                userRepository.save(botUser);
                logger.log(Level.INFO, "Новый пользователь нажал START");
                sendMess(messageGenerator.forAdmin(botAdminId, botUser.getBotUserName(), botUser.getBotUserId()));
            }
            sendMess(messageGenerator.greetingUser(botUser.getBotUserId(), botUser.getBotUserName()));
            sendMess(messageGenerator.requestGeoPosition(botUser.getBotUserId()));
        }
        else if(update.getMessage().hasLocation()) {
            restClient.setGeoLatitude(update.getMessage().getLocation().getLatitude());
            restClient.setGeoLongitude(update.getMessage().getLocation().getLongitude());
            botUser.setBotUserId(update.getMessage().getFrom().getId());
            botUser.setBotUserName(update.getMessage().getFrom().getFirstName());
            if (userRepository.existsByBotUserId(this.botUser.getBotUserId())) {
                logger.log(Level.INFO, "Существующий пользователь продолжил сессию, получена геопозиция");}
            else {
                botUser = new BotUser(update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getId());
                userRepository.save(botUser);
                logger.log(Level.INFO, "Новый пользователь направил геопозицию без START");
                sendMess(messageGenerator.forAdmin(botAdminId, botUser.getBotUserName(), botUser.getBotUserId()));
            }
            try {
                sendMess(messageGenerator.weatherForecastForGeoposition(botUser.getBotUserId()));
            } catch (JsonProcessingException e) {
                logger.log(Level.INFO, "JSON {0}", e.toString());
            } catch (HttpClientErrorException e) {
                logger.log(Level.INFO, "HttpClientErrorException {0}", e.toString());
            } catch (ParseException e) {
                logger.log(Level.INFO, "ParseException {0}", e.toString());
            } catch (InterruptedException ex) {
                logger.log(Level.INFO, "InterruptedException {0}", ex.toString());
            } catch (ExecutionException ex) {
                logger.log(Level.INFO, "ExecutionException {0}", ex.toString());
            }
        }
        else if (!"/start".equals(update.getMessage().getText()) && !update.getMessage().hasLocation()) {
            String city = update.getMessage().getText();
            botUser.setBotUserId(update.getMessage().getFrom().getId());
            botUser.setBotUserName(update.getMessage().getFrom().getFirstName());
            if (userRepository.existsByBotUserId(this.botUser.getBotUserId())) {
                logger.log(Level.INFO, "Существующий пользователь продолжил сессию");}
            else {
                botUser = new BotUser(update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getId());
                userRepository.save(botUser);
                logger.log(Level.INFO, "Новый пользователь ввел название города без START");
                sendMess(messageGenerator.forAdmin(botAdminId, botUser.getBotUserName(), botUser.getBotUserId()));
            }
            try {
                sendMess(messageGenerator.weatherForecastForCityName(botUser.getBotUserId(), city));
                if(!userFindCityRepository.existsByCityName(city)) {
                    botUserFindCity = new BotUserFindCity();
                    botUserFindCity.setCityName(city);
                    botUserFindCity.setCityFindCount(1);
                    userFindCityRepository.save(botUserFindCity);
                    logger.log(Level.INFO, "Новый город добавлен, {0}", botUserFindCity.getCityName());
                }
                else {
                    for (BotUserFindCity c : userFindCityRepository.findAll()) {
                        if (c.getId().equals(userRepository.findByBotUserId(update.getMessage().getFrom().getId()).getID()))
                            c.setCityFindCount(c.getCityFindCount() + 1);
                        }
                    userFindCityRepository.save(botUserFindCity);
                    logger.log(Level.INFO, "Счет обновлен: {0}", botUserFindCity.getCityFindCount());
                }
            } catch (HttpClientErrorException e) {
                sendMess(messageGenerator.cityNotFound(botUser.getBotUserId()));
                logger.log(Level.WARNING, "Такого города нет, {0}", e.toString());
            } catch (JsonProcessingException e) {
                logger.log(Level.INFO, "JSON {0}", e.toString());
            } catch (ParseException e) {
                logger.log(Level.INFO, "ParseException {0}", e.toString());
            } catch (InterruptedException ex) {
                logger.log(Level.INFO, "InterruptedException {0}", ex.toString());
            } catch (ExecutionException ex) {
                logger.log(Level.INFO, "ExecutionException {0}", ex.toString());
            }
        }
    }
    
    public void sendMess(SendMessage message) {
        try {
            execute(message);
            Thread currentThread = Thread.currentThread();
            logger.log(Level.INFO, "Отправка сообщения, поток : {0}", currentThread.getName());
        }
        catch (TelegramApiException e){
            logger.log(Level.WARNING, "Throw TelegramApiException {0}", e.toString());
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
