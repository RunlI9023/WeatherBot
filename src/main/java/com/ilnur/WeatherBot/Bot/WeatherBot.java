package com.ilnur.WeatherBot.Bot;

import com.ilnur.WeatherBot.Entities.BotUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilnur.WeatherBot.RestClient.BotRestClient;
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
import java.util.concurrent.ExecutionException;
import com.ilnur.WeatherBot.Service.BotService;

@Component
public class WeatherBot extends TelegramLongPollingBot {
    
    @Value("${botApiToken}")
    private String botApiToken;
    @Value("${botUserName}")
    private String botUserName;
    @Value("${botAdminId}")
    private Long botAdminId;
    private final BotUser user;
    private final BotRestClient restClient;
    private final MessageGenerator messageGenerator;
    private final BotService service;
    private final KeyBoard keyboard;
    private SendMessage geoMessage;
    private static final Logger logger = Logger.getLogger(WeatherBot.class.getName());
    private final String startMessage = "Для того, чтобы узнать погоду, введи название нужного города или нажми на кнопку в меню для получения погоды по текущей геолокации";
    
    @Autowired
    public WeatherBot(BotRestClient restClient, MessageGenerator messageGenerator, BotUser user, KeyBoard keyboard, BotService service) {
        this.restClient = restClient;
        this.messageGenerator = messageGenerator;
        this.user = user;
        this.keyboard = keyboard;
        this.service = service;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && !update.getMessage().hasLocation() && update.getMessage().getText().equals("/start")) {
            user.setBotUserId(update.getMessage().getFrom().getId());
            user.setBotUserName(update.getMessage().getFrom().getFirstName());
            if (service.existUserByTgId(user.getBotUserId())) {
                user.setID(service.getBotUser(update.getMessage().getFrom().getId()).getID());
                user.setBotUserId(service.getBotUser(update.getMessage().getFrom().getId()).getBotUserId());
                user.setBotUserName(service.getBotUser(update.getMessage().getFrom().getId()).getBotUserName());
                service.getBotUser(user.getID());
                logger.log(Level.INFO, "Существующий пользователь");
            }
            else {
                service.saveNewUser(update.getMessage().getFrom().getId(), update.getMessage().getFrom().getFirstName());
                logger.log(Level.INFO, "Новый пользователь нажал START");
                messageSender(messageGenerator.forAdmin(botAdminId, update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getId()));
            }
            geoMessage = new SendMessage();
            geoMessage.setReplyMarkup(service.getKeyBoard().geoLocationReplyKeyboard(this.user));
            geoMessage.setText("Для того, чтобы узнать погоду, введи название нужного города или нажми на кнопку в меню для получения погоды по текущей геолокации");
            geoMessage.setChatId(user.getBotUserId());
            messageSender(messageGenerator.greetingUser(user.getBotUserId(), user.getBotUserName()));
            messageSender(geoMessage);
        }
        else if(update.getMessage().hasLocation()) {
            user.setBotUserId(update.getMessage().getFrom().getId());
            user.setBotUserName(update.getMessage().getFrom().getFirstName());
            restClient.setGeoLatitude(update.getMessage().getLocation().getLatitude());
            restClient.setGeoLongitude(update.getMessage().getLocation().getLongitude());
            if (service.existUserById(user.getBotUserId())) {
                logger.log(Level.INFO, "Существующий пользователь продолжил сессию, получена геопозиция");}
            else {
                service.saveNewUser(update.getMessage().getFrom().getId(), update.getMessage().getFrom().getFirstName());
                logger.log(Level.INFO, "Новый пользователь направил геопозицию без START");
                messageSender(messageGenerator.forAdmin(botAdminId, user.getBotUserName(), user.getBotUserId()));
            }
            try {
                messageSender(messageGenerator.weatherForecastForGeoposition(user.getBotUserId()));
//                service.saveNewUserCity(user.getBotUserId(), messageGenerator.weatherForecastForGeoposition(user.getBotUserId()).);
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
        else if (update.hasMessage() && !"/start".equals(update.getMessage().getText()) && !update.getMessage().hasLocation()) {
            String city = update.getMessage().getText();
            user.setBotUserId(update.getMessage().getFrom().getId());
            user.setBotUserName(update.getMessage().getFrom().getFirstName());
            if (service.existUserByTgId(user.getBotUserId())) {
                user.setID(service.getBotUserByTgId(user.getBotUserId()).getID());
                user.setBotUserId(service.getBotUserByTgId(user.getBotUserId()).getBotUserId());
                user.setBotUserName(service.getBotUserByTgId(user.getBotUserId()).getBotUserName());
                logger.log(Level.INFO, "Существующий пользователь продолжил сессию");}
            else {
                service.saveNewUser(update.getMessage().getFrom().getId(), update.getMessage().getFrom().getFirstName());
                logger.log(Level.INFO, "Новый пользователь ввел название города без START");
                messageSender(messageGenerator.forAdmin(botAdminId, user.getBotUserName(), user.getBotUserId()));
            }
            geoMessage = new SendMessage();
            geoMessage.setReplyMarkup(service.getKeyBoard().geoLocationReplyKeyboard(service.getBotUserByTgId(user.getBotUserId())));
            geoMessage.setText("Пожалуйста, ожидайте...");
            geoMessage.setChatId(user.getBotUserId());
            try {
                messageSender(geoMessage);
                messageSender(messageGenerator.weatherForecastForCityName(user.getBotUserId(), city));
                service.saveNewUserCity(user.getBotUserId(), city);
            } catch (HttpClientErrorException e) {
                messageSender(messageGenerator.cityNotFound(user.getBotUserId()));
                logger.log(Level.WARNING, "Такого города нет, {0}", e.toString());
            } catch (JsonProcessingException e) {
                logger.log(Level.INFO, "JSON {0}", e.toString());
            } catch (ParseException e) {
                logger.log(Level.INFO, "ParseException {0}", e.toString());
            } catch (InterruptedException ex) {
                logger.log(Level.INFO, "InterruptedException {0}", ex.toString());
            } catch (ExecutionException ex) {
                logger.log(Level.INFO, "ExecutionException {0}", ex.toString());
                messageSender(messageGenerator.cityNotFound(user.getBotUserId()));
            } catch (NullPointerException ex) {
                logger.log(Level.WARNING, "NullPointerException {0}", ex.toString());
            }
        }
    }
    
    public void messageSender(SendMessage message) {
        try {
            execute(message);
            //Thread currentThread = Thread.currentThread();
            //logger.log(Level.INFO, "Отправка сообщения, поток : {0}", currentThread.getName());
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
