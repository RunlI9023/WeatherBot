/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilnur.BenderBot.Weather.CurrentWeatherForGeoPosition.ExampleCurrentGeo;
import com.ilnur.BenderBot.Rest.BenderBotRestClient;
import com.ilnur.BenderBot.Weather.CurrentWeather.WeatherNowCurrent;
import com.ilnur.BenderBot.Weather.WeatherForecastForGeoPosition.ExampleForecastGeo;
import com.vdurmont.emoji.EmojiParser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author ЭмирНурияКарим
 */
@Component
public class BenderBot extends TelegramLongPollingBot {
    
    @Autowired
    BenderBotRestClient benderBotRestClient;
    @Autowired
    private WeatherNowCurrent weatherNow;
    @Autowired
    private ExampleCurrentGeo exampleGeo;
    @Autowired
    ExampleForecastGeo exampleForecastGeo;
    @Autowired
    public ObjectMapper objectMapper;
    long id;
    long userId;
    String userName;
    Double geoLatitude;
    Double geoLongitude;
    String humid = ":droplet:";
    String humidPars = EmojiParser.parseToUnicode(humid);
    String therm = ":thermometer:";
    String thermPars = EmojiParser.parseToUnicode(therm);
    String wind = ":wind_blowing_face:";
    String windPars = EmojiParser.parseToUnicode(wind);
    String cloud = ":cloud:";
    String cloudPars = EmojiParser.parseToUnicode(cloud);
    
    @Override
    public void onUpdateReceived(Update update) {
            
        if (!update.getMessage().hasLocation() && update.getMessage().getText().equals("/start")) {
            id = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();
            System.out.println("Имя пользователя: " + userName + ", id: " + id + ", userID: " + userId);
            sendMess(sendGeoPosition(userId));
        }
        else if(update.getMessage().hasLocation()) {
                userId = update.getMessage().getFrom().getId();
                geoLatitude = update.getMessage().getLocation().getLatitude();
                geoLongitude = update.getMessage().getLocation().getLongitude();
                try {
                    exampleGeo = objectMapper.readValue(benderBotRestClient.getGeoWeather(
                     geoLatitude.toString(), 
                     geoLongitude.toString()), ExampleCurrentGeo.class);
                            sendMess(weatherForGeoposition(userId));
                } catch (JsonProcessingException ex) {
                    System.out.println("JsonProcessingException: " + ex);
                } catch (HttpClientErrorException e) {
                    System.out.println("Город не найден. " + e);
                }
            }
//        else if(update.getMessage().hasLocation() && update.getMessage().getText().equals("5 day weather forecast")) {
//            String str = update.getMessage().getText();    
//            userId = update.getMessage().getFrom().getId();
//                geoLatitude = update.getMessage().getLocation().getLatitude();
//                geoLongitude = update.getMessage().getLocation().getLongitude();
//                try {
//                    exampleForecastGeo = objectMapper.readValue(benderBotRestClient.getGeoWeatherForecast(
//                     geoLatitude.toString(), 
//                     geoLongitude.toString()), ExampleForecastGeo.class);
//                            //sendMess(weatherForGeoposition(userId));
//                            System.out.println(str);
//                } catch (JsonProcessingException ex) {
//                    System.out.println("JsonProcessingException: " + ex);
//                } catch (HttpClientErrorException e) {
//                    System.out.println("City not found: " + e);
//                }
//            }
        else if (!"/start".equals(update.getMessage().getText()) && !update.getMessage().hasLocation()) {
            //sendMess(sendGeoPosition(userId));
            String city = update.getMessage().getText();
            try {
                weatherNow = objectMapper.readValue(benderBotRestClient.getCurrentWeather(city), WeatherNowCurrent.class);
                sendMess(sendCityNameWeather(userId));
            } catch (JsonProcessingException ex) {
                System.out.println("Ошибка JsonProcessingException: "+ ex);
            } catch (HttpClientErrorException e) {
                sendMess(sendCityNotFound(userId));
                System.out.println("Город не найден: " + e);
            }
        }
    }

    @Override
    public String getBotToken() {
        return "7030876343:AAE0s4pLgyoTcqXikVAPf6jlIZeBDiPwvlM";
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
    
    public SendMessage sendCityNotFound(Long who) {
        SendMessage geoMessage = SendMessage.builder()
                .chatId(who.toString())
                .text("Город не найден. Проверьте правильность ввода и повторите попытку.")
                .build();
        return geoMessage;
    }
    
    public SendMessage weatherForGeoposition(Long who) {
        SendMessage weatherMessage = SendMessage.builder()
                .chatId(who.toString())
                .text(
                geoLatitude + 
                "\n" + geoLongitude +
                "\nСейчас в г. " + exampleGeo.getName() + ": " + "\n" +
                        exampleGeo.getDescription() +
                "\n" + thermPars + " Температура днем/ночью: " + 
                "\n" + Math.round(exampleGeo.getMain().getTempMax()) + " \u2103" + " / " +
                       Math.round(exampleGeo.getMain().getTempMin()) + " \u2103" + 
                               ", ощущается как: " + Math.round(exampleGeo.getMain().getFeelsLike()) + " \u2103" +
                "\n" + humidPars + " Влажность: " + exampleGeo.getMain().getHumidity() + "%" +
                "\n" + cloudPars + " Облачность: " + exampleGeo.getClouds().getAll() + "%" +
                "\n" + windPars + " Скорость ветра: " + exampleGeo.getWind().getSpeed()+ " км/ч.")
                .build();
        return weatherMessage;
    }
    
    public SendMessage sendCityNameWeather(Long who) {
        SendMessage geoMessage = SendMessage.builder()
                .chatId(who.toString())
                .text("Сейчас в г. " + weatherNow.getName() + ": " + "\n" + weatherNow.getDescription() +
                "\n" + thermPars + " Температура днем/ночью: " + 
                "\n" + Math.round(weatherNow.getMain().getTempMax()) + " \u2103" + " / " +
                       Math.round(weatherNow.getMain().getTempMin()) + " \u2103" + 
                                ", ощущается как: " + Math.round(weatherNow.getMain().getFeelsLike()) + " \u2103" +
                "\n" + humidPars + " Влажность: " + weatherNow.getMain().getHumidity() + "%" +
                "\n" + cloudPars + " Облачность: "  + weatherNow.getClouds().getAll() + "%" +
                "\n" + windPars + " Скорость ветра: "  + weatherNow.getWind().getSpeed() + " км/ч.")
                .build();
        return geoMessage;
    }
    
    public SendMessage sendGeoPosition(Long who) {
        SendMessage geoMessage = SendMessage.builder()
                .chatId(who.toString())
                .replyMarkup(keyboard())
                .text("Для того, чтобы узнать текущую погоду, "
                        + "введите название города или нажмите на кнопку \"Текущая погода по геолокации\" в меню")
                .build();
        return geoMessage;
    }
    
    public ReplyKeyboardMarkup keyboard() {
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                KeyboardButton geoCurrentWeatherButton = new KeyboardButton();
                //KeyboardButton geoWeatherForecastButton = new KeyboardButton();
                geoCurrentWeatherButton.setRequestLocation(true);
                geoCurrentWeatherButton.setText("Текущая погода по геолокации");
                //geoWeatherForecastButton.setRequestLocation(true);
                //geoWeatherForecastButton.setText("5 day weather forecast");
                row.add(geoCurrentWeatherButton);
                //row.add(geoWeatherForecastButton);
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                keyboardMarkup.setSelective(true);
                keyboardMarkup.setResizeKeyboard(true);
                    return keyboardMarkup;
        }
    
//    public InlineKeyboardMarkup geoLocationInlineKeyboard() {
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
//        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//        inlineKeyboardButton1.setText("Geoposition weather");
//        rowInline1.add(inlineKeyboardButton1);
//        rowsInline.add(rowInline1);
//        markupInline.setKeyboard(rowsInline);
//        return markupInline;
//    }
}
