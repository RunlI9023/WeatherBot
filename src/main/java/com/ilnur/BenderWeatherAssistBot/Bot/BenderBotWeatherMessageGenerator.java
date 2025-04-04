package com.ilnur.BenderWeatherAssistBot.Bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilnur.BenderWeatherAssistBot.BotRest.BenderBotRestClient;
import com.ilnur.BenderWeatherAssistBot.CurrentWeatherForCityName.WeatherNowCurrent;
import com.ilnur.BenderWeatherAssistBot.CurrentWeatherForGeoPosition.ExampleCurrentGeo;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForGeoPosition.ExampleForecastGeo;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName.ExampleForecastForCityName;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName.ResultForecastMessage;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName.ResultForecastMessageEnd;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForGeoPosition.ResultForecastMessageEndForGeoposition;
import com.ilnur.BenderWeatherAssistBot.WeatherForecastForGeoPosition.ResultForecastMessageForGeoposition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


/** Exceptions for handling:
 * java.lang.RuntimeException: Error executing org.telegram.telegrambots.meta.api.methods.send.SendMessage query: 
 * [400] Bad Request: message is too long
 * org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException: 
 * Error executing org.telegram.telegrambots.meta.api.methods.send.SendMessage query: 
 * [400] Bad Request: message is too long
 * 
 * добавить обработку эмодзи, если в прогнозе меньше 24 ч., иначе выводит null, сделать надпись н/д
 * 
 * Погода сейчас: сделать так, чтобы днем солнце, а ночью луна, если описание "ясная погода", использовать текущее время
 * 
 * добавить в список эемодзи:
 * дождь 
 * 
 * выравнивание сообщения сделать, эмодзи описания погоды сегодян с 10 символа, дней недели с 15 символа начинать
 * далее один tab (4 пробела)
 */

@Component
public class BenderBotWeatherMessageGenerator {
    
    private WeatherNowCurrent weatherNowCurrent;
    private ExampleForecastForCityName weatherForecastForCityName;
    private ExampleCurrentGeo exampleCurrentGeo;
    private ExampleForecastGeo exampleForecastGeo;
    private BenderBotRestClient benderBotRestClient;
    private BenderBotWeatherEmoji weatherEmoji;
    private ReplyKeyboardMarkup geoLocationReplyKeyboard;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("EEEE", Locale.of("ru", "RU"));
    String today = simpleDateFormat.format(new Date());

    public BenderBotWeatherMessageGenerator() {
    }

    @Autowired
    public BenderBotWeatherMessageGenerator(BenderBotRestClient benderBotRestClient, BenderBotWeatherEmoji weatherEmoji, ReplyKeyboardMarkup geoLocationReplyKeyboard) {
        this.benderBotRestClient = benderBotRestClient;
        this.weatherEmoji = weatherEmoji;
        this.geoLocationReplyKeyboard = geoLocationReplyKeyboard;
    }
    
    public SendMessage greetingUser(Long who, String userName) {
        SendMessage greetingUserMessage = SendMessage.builder()
                .chatId(who.toString())
                .text("Привет, " + userName + "!")
                .build();
        return greetingUserMessage;
    }

    public SendMessage forAdmin(Long who, String userName, long userId) {
        SendMessage forAdmin = SendMessage.builder()
                .chatId(who.toString())
                .text("Присоединился новый пользователь, имя: " + userName + ", ID: " + userId)
                .build();
        return forAdmin;
    }

    public SendMessage requestGeoPosition(Long who) {
        SendMessage requestGeoPositionMessage = SendMessage.builder()
                .chatId(who.toString())
                .replyMarkup(geoLocationReplyKeyboard)
                .text("Для того, чтобы узнать погоду, "
                        + "введи название нужного города или нажми на кнопку "
                        + "в меню для получения погоды по текущей геолокации")
                .build();
        return requestGeoPositionMessage;
    }
    
    public SendMessage cityNotFound(Long who) {
        SendMessage cityNotFoundMessage = SendMessage.builder()
                .chatId(who.toString())
                .text("Город не найден. Проверь корректность ввода и повтори попытку.")
                .build();
        return cityNotFoundMessage;
    }
    
    public SendMessage weatherForecastForGeoposition(Long who) throws JsonProcessingException, ParseException {
        exampleCurrentGeo = benderBotRestClient.getCurrentWeatherForGeoposition(
                     benderBotRestClient.getGeoLatitude().toString(), 
                     benderBotRestClient.getGeoLongitude().toString());
        exampleForecastGeo = benderBotRestClient.getWeatherForecastForGeoposition(
                     benderBotRestClient.getGeoLatitude().toString(), 
                     benderBotRestClient.getGeoLongitude().toString());
        
        String weatherEmojiForGeoposition = "";
        switch(exampleCurrentGeo.getDescription()) {
                            case "переменная облачность" -> {weatherEmojiForGeoposition = weatherEmoji.getCloudPars();
                                break;}
                            case "облачно с прояснениями" -> {weatherEmojiForGeoposition = weatherEmoji.getWhiteSunBehindCloudPars();
                                break;}
                            case "пасмурно" -> {weatherEmojiForGeoposition = weatherEmoji.getCloudPars();
                                break;}
                            case "небольшая облачность" -> {weatherEmojiForGeoposition = weatherEmoji.getWhiteSunSmallCloudPars();
                                break;}
                            case "ясно" -> {weatherEmojiForGeoposition = weatherEmoji.getCrescentMoonPars();
                                break;}
                            case "небольшой снег" -> {weatherEmojiForGeoposition = weatherEmoji.getCloudSnowPars();
                                break;}
                            case "небольшой дождь" -> {weatherEmojiForGeoposition = weatherEmoji.getCloudRainPars();
                                break;}
                            case "снег" -> {weatherEmojiForGeoposition = weatherEmoji.getSnowflakePars();
                                break;}
        }
        String textOutForGeoposition = "";
        String textInForGeoposition;
        String textInTabOne = "";
        int textInTabOneLength = textInTabOne.length();
        String textInTabTwo = "";
        int textInTabTwoLength = textInTabTwo.length();
        String textInTabThree = "";
        int textInTabThreeLength = textInTabThree.length();
        String textInTabFour = "";
        int textInTabFourLength = textInTabFour.length();
        for (int i = 0; i < getResultForecastObjectsForGeoposition().size(); i++) {     
            if (getResultForecastObjectsForGeoposition().get(i).getDayOfWeek().equals(today)){
/*
*часть для сегодня по часам
*/                  
            textInForGeoposition = "\n" +    
                getResultForecastObjectsForGeoposition().get(i).getDate() + ":" + textInTabOne +
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmoji() + textInTabTwo +
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMaximum()) + "\u2103" + textInTabThree + 
                weatherEmoji.getHumidPars() + textInTabFour + getResultForecastObjectsForGeoposition().get(i).getHumidity() + "%"
//                + "    " + getResultForecastObjects().get(i).getDescription()
                ;
                textOutForGeoposition += textInForGeoposition;
            }
/*
*часть для дней недели
*/            
            else {
            textInForGeoposition = "\n" +    
                getResultForecastObjectsForGeoposition().get(i).getDate() + ": " +
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmojiOfDay() + "  " +
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmojiOfNight() + "  " +
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMaximum()) + "\u2103" + "  " + 
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMinimum()) + "\u2103" + "  " +
                weatherEmoji.getHumidPars() + " " + getResultForecastObjectsForGeoposition().get(i).getHumidity() + "%"
//                + " " +
//                getResultForecastObjects().get(i).getDescriptionOfDay() + ", " + 
//                getResultForecastObjects().get(i).getDescriptionOfNight()
                ;
                textOutForGeoposition += textInForGeoposition;
            } 
        }
        
        SendMessage weatherForecastForGeopositionMessage = SendMessage.builder()
                .chatId(who.toString())
                .text("Прогноз погоды для г. " + exampleCurrentGeo.getName() + ": " + 
                "\nСейчас: " + weatherEmojiForGeoposition + "  " + Math.round(exampleCurrentGeo.getMain().getTempMax()) + " \u2103" +
                ", ощущается как: " + Math.round(exampleCurrentGeo.getMain().getFeelsLike()) + " \u2103" + "  " +
                        weatherEmoji.getHumidPars() + "  " + exampleCurrentGeo.getMain().getHumidity() + "%"
//                        + weatherNowCurrent.getDescription() 
                        + textOutForGeoposition)
                .build();
        return weatherForecastForGeopositionMessage;
    }
    
    public SendMessage weatherForecastForCityName(Long who, String city) throws JsonProcessingException, ParseException {
        weatherForecastForCityName = benderBotRestClient.getWeatherForecastForCityName(city);
        weatherNowCurrent = benderBotRestClient.getCurrentWeatherForCityName(city);
        String currentWeatherEmoji = "";
        switch(weatherNowCurrent.getDescription()) {
                            case "переменная облачность" -> {currentWeatherEmoji = weatherEmoji.getCloudPars();
                                break;}
                            case "облачно с прояснениями" -> {currentWeatherEmoji = weatherEmoji.getWhiteSunBehindCloudPars();
                                break;}
                            case "пасмурно" -> {currentWeatherEmoji = weatherEmoji.getCloudPars();
                                break;}
                            case "небольшая облачность" -> {currentWeatherEmoji = weatherEmoji.getWhiteSunSmallCloudPars();
                                break;}
                            case "ясно" -> {currentWeatherEmoji = weatherEmoji.getCrescentMoonPars();
                                break;}
                            case "небольшой снег" -> {currentWeatherEmoji = weatherEmoji.getCloudSnowPars();
                                break;}
                            case "небольшой дождь" -> {currentWeatherEmoji = weatherEmoji.getCloudRainPars();
                                break;}
                            case "снег" -> {currentWeatherEmoji = weatherEmoji.getSnowflakePars();
                                break;}
        }
        String textOut = "";
        String textIn;
        String textInTabOne = "";
        int textInTabOneLength = textInTabOne.length();
        String textInTabTwo = "";
        int textInTabTwoLength = textInTabTwo.length();
        String textInTabThree = "";
        int textInTabThreeLength = textInTabThree.length();
        String textInTabFour = "";
        int textInTabFourLength = textInTabFour.length();
        for (int i = 0; i < getResultForecastObjectsForCityName().size(); i++) {     
            if (getResultForecastObjectsForCityName().get(i).getDayOfWeek().equals(today)){
/*
*часть для сегодня по часам
*/                  
            textIn = "\n" +    
                getResultForecastObjectsForCityName().get(i).getDate() + ":" + textInTabOne +
                getResultForecastObjectsForCityName().get(i).getDescriptionEmoji() + textInTabTwo +
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMaximum()) + "\u2103" + textInTabThree + 
                weatherEmoji.getHumidPars() + textInTabFour + getResultForecastObjectsForCityName().get(i).getHumidity() + "%"
//                + "    " + getResultForecastObjects().get(i).getDescription()
                ;
                textOut += textIn;
            }
/*
*часть для дней недели
*/            
            else {
            textIn = "\n" +    
                getResultForecastObjectsForCityName().get(i).getDate() + ": " +
                getResultForecastObjectsForCityName().get(i).getDescriptionEmojiOfDay() + "  " +
                getResultForecastObjectsForCityName().get(i).getDescriptionEmojiOfNight() + "  " +
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMaximum()) + "\u2103" + "  " + 
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMinimum()) + "\u2103" + "  " +
                weatherEmoji.getHumidPars() + " " + getResultForecastObjectsForCityName().get(i).getHumidity() + "%"
//                + " " +
//                getResultForecastObjects().get(i).getDescriptionOfDay() + ", " + 
//                getResultForecastObjects().get(i).getDescriptionOfNight()
                ;
                textOut += textIn;
            } 
        }

        SendMessage weatherForecastForCityNameMessage = SendMessage.builder()
                .chatId(who.toString())
                .text("Прогноз погоды для г. " + weatherNowCurrent.getName() + ": " + 
                "\nСейчас: " + currentWeatherEmoji + "  " + Math.round(weatherNowCurrent.getMain().getTempMax()) + " \u2103" +
                ", ощущается как: " + Math.round(weatherNowCurrent.getMain().getFeelsLike()) + " \u2103" + "  " +
                        weatherEmoji.getHumidPars() + "  " + weatherNowCurrent.getMain().getHumidity() + "%"
//                        + weatherNowCurrent.getDescription() 
                        + textOut)
                .build();
        return weatherForecastForCityNameMessage;
    }
    
    public List<ResultForecastMessageEnd> getResultForecastObjectsForCityName() throws ParseException {
        List<ResultForecastMessageEnd> forecastMessageListEnd = new ArrayList<>();
        String dayOfWeek;
        Double tmpMax;
        Double tmpMin;
        Integer humid;
        
        for (Map.Entry<String, List<ResultForecastMessage>> entry : weatherForecastForCityName.resultForecastMessage().entrySet()) {
            ResultForecastMessageEnd resultForecastMessageEnd = new ResultForecastMessageEnd();
            Date dt = simpleDateFormat.parse(entry.getKey()); 
            if (today.equals(entry.getKey().substring(0, 10))) {
                for (int i = 0; i < entry.getValue().size() ; i++) {
                    ResultForecastMessageEnd resultForecastMessageEnd2 = new ResultForecastMessageEnd();
                    resultForecastMessageEnd2.setDate(entry.getValue().get(i).getDate().substring(11, 16) + " ч");
                    resultForecastMessageEnd2.setDayOfWeek(today);
                    resultForecastMessageEnd2.setTempMaximum(entry.getValue().get(i).getTempMaximum());
                    resultForecastMessageEnd2.setHumidity(entry.getValue().get(i).getHumidity());
                    resultForecastMessageEnd2.setDescription(entry.getValue().get(i).getDescription());
                        switch(entry.getValue().get(i).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageEnd2.setDescriptionEmoji(weatherEmoji.getCloudPars());
                                break;}
                            case "облачно с прояснениями" -> {resultForecastMessageEnd2.setDescriptionEmoji(weatherEmoji.getWhiteSunBehindCloudPars());
                                break;}
                            case "пасмурно" -> {resultForecastMessageEnd2.setDescriptionEmoji(weatherEmoji.getCloudPars());
                                break;}
                            case "небольшая облачность" -> {resultForecastMessageEnd2.setDescriptionEmoji(weatherEmoji.getWhiteSunSmallCloudPars());
                                break;}
                            case "ясно" -> {resultForecastMessageEnd2.setDescriptionEmoji(weatherEmoji.getSunnyPars());
                                break;}
                            case "небольшой снег" -> {resultForecastMessageEnd2.setDescriptionEmoji(weatherEmoji.getCloudSnowPars());
                                break;}
                            case "небольшой дождь" -> {resultForecastMessageEnd2.setDescriptionEmoji(weatherEmoji.getCloudRainPars());
                                break;}
                            case "снег" -> {resultForecastMessageEnd2.setDescriptionEmoji(weatherEmoji.getSnowflakePars());
                                break;
                            }
                        }
                    forecastMessageListEnd.add(resultForecastMessageEnd2);
                }
            }
            else {
            dayOfWeek = simpleDateFormat2.format(dt);
            tmpMax = Collections.max(entry.getValue()
                    .stream()
                    .map(r -> r.getTempMaximum())
                    .collect(Collectors.toList()));
            tmpMin = Collections.min(entry.getValue()
                    .stream()
                    .map(r -> r.getTempMinimum())
                    .collect(Collectors.toList()));
            humid = Collections.max(entry.getValue()
                    .stream()
                    .map(r -> r.getHumidity())
                    .collect(Collectors.toList()));
            
            resultForecastMessageEnd.setDate(dayOfWeek);
            resultForecastMessageEnd.setDayOfWeek(dayOfWeek);
            resultForecastMessageEnd.setTempMaximum(tmpMax);
            resultForecastMessageEnd.setTempMinimum(tmpMin);
            resultForecastMessageEnd.setHumidity(humid);
            for (int j = 0; j < entry.getValue().size(); j++) {
                switch (entry.getValue().get(j).getDate().substring(11, 16)) {
                    case "12:00" -> {
                        resultForecastMessageEnd.setDescriptionOfDay(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageEnd.setDescriptionEmojiOfDay(weatherEmoji.getCloudPars());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageEnd.setDescriptionEmojiOfDay(weatherEmoji.getWhiteSunBehindCloudPars());
                            break;}
                            case "пасмурно" -> {resultForecastMessageEnd.setDescriptionEmojiOfDay(weatherEmoji.getCloudPars());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageEnd.setDescriptionEmojiOfDay(weatherEmoji.getWhiteSunSmallCloudPars());
                            break;}
                            case "ясно" -> {resultForecastMessageEnd.setDescriptionEmojiOfDay(weatherEmoji.getSunnyPars());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageEnd.setDescriptionEmojiOfDay(weatherEmoji.getCloudSnowPars());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageEnd.setDescriptionEmojiOfDay(weatherEmoji.getCloudRainPars());
                            break;}
                            case "снег" -> {resultForecastMessageEnd.setDescriptionEmojiOfDay(weatherEmoji.getSnowflakePars());
                            break;
                            }
                        }
                    }
                    case "00:00" -> {
                        resultForecastMessageEnd.setDescriptionOfNight(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageEnd.setDescriptionEmojiOfNight(weatherEmoji.getCloudPars());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageEnd.setDescriptionEmojiOfNight(weatherEmoji.getWhiteSunBehindCloudPars());
                            break;}
                            case "пасмурно" -> {resultForecastMessageEnd.setDescriptionEmojiOfNight(weatherEmoji.getCloudPars());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageEnd.setDescriptionEmojiOfNight(weatherEmoji.getWhiteSunSmallCloudPars());
                            break;}
                            case "ясно" -> {resultForecastMessageEnd.setDescriptionEmojiOfNight(weatherEmoji.getCrescentMoonPars());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageEnd.setDescriptionEmojiOfNight(weatherEmoji.getCloudSnowPars());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageEnd.setDescriptionEmojiOfNight(weatherEmoji.getCloudRainPars());
                            break;}
                            case "снег" -> {resultForecastMessageEnd.setDescriptionEmojiOfNight(weatherEmoji.getSnowflakePars());
                            break;}
                        }
                    }
                    default -> {
                        resultForecastMessageEnd.setDescription(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageEnd.setDescriptionEmoji(weatherEmoji.getCloudPars());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageEnd.setDescriptionEmoji(weatherEmoji.getWhiteSunBehindCloudPars());
                            break;}
                            case "пасмурно" -> {resultForecastMessageEnd.setDescriptionEmoji(weatherEmoji.getCloudPars());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageEnd.setDescriptionEmoji(weatherEmoji.getWhiteSunSmallCloudPars());
                            break;}
                            case "ясно" -> {resultForecastMessageEnd.setDescriptionEmoji(weatherEmoji.getCrescentMoonPars());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageEnd.setDescriptionEmoji(weatherEmoji.getCloudSnowPars());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageEnd.setDescriptionEmoji(weatherEmoji.getCloudRainPars());
                            break;}
                            case "снег" -> {resultForecastMessageEnd.setDescriptionEmoji(weatherEmoji.getSnowflakePars());
                            break;}
                        }
                    }
                }
            }
            forecastMessageListEnd.add(resultForecastMessageEnd);
            }
        }
        return forecastMessageListEnd;
    }
    
    public List<ResultForecastMessageEndForGeoposition> getResultForecastObjectsForGeoposition() throws ParseException {
        List<ResultForecastMessageEndForGeoposition> forecastMessageListEndForGeoposition = new ArrayList<>();
        String dayOfWeek;
        Double tmpMax;
        Double tmpMin;
        Integer humid;
        
        for (Map.Entry<String, List<ResultForecastMessageForGeoposition>> entry : exampleForecastGeo.resultForecastMessageForGeoposition().entrySet()) {
            ResultForecastMessageEndForGeoposition resultForecastMessageEndForGeoposition = new ResultForecastMessageEndForGeoposition();
            Date dt = simpleDateFormat.parse(entry.getKey()); 
            if (today.equals(entry.getKey().substring(0, 10))) {
                for (int i = 0; i < entry.getValue().size() ; i++) {
                    ResultForecastMessageEndForGeoposition resultForecastMessageEndForGeoposition2 = new ResultForecastMessageEndForGeoposition();
                    resultForecastMessageEndForGeoposition2.setDate(entry.getValue().get(i).getDate().substring(11, 16) + " ч");
                    resultForecastMessageEndForGeoposition2.setDayOfWeek(today);
                    resultForecastMessageEndForGeoposition2.setTempMaximum(entry.getValue().get(i).getTempMaximum());
                    resultForecastMessageEndForGeoposition2.setHumidity(entry.getValue().get(i).getHumidity());
                    resultForecastMessageEndForGeoposition2.setDescription(entry.getValue().get(i).getDescription());
                        switch(entry.getValue().get(i).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageEndForGeoposition2.setDescriptionEmoji(weatherEmoji.getCloudPars());
                                break;}
                            case "облачно с прояснениями" -> {resultForecastMessageEndForGeoposition2.setDescriptionEmoji(weatherEmoji.getWhiteSunBehindCloudPars());
                                break;}
                            case "пасмурно" -> {resultForecastMessageEndForGeoposition2.setDescriptionEmoji(weatherEmoji.getCloudPars());
                                break;}
                            case "небольшая облачность" -> {resultForecastMessageEndForGeoposition2.setDescriptionEmoji(weatherEmoji.getWhiteSunSmallCloudPars());
                                break;}
                            case "ясно" -> {resultForecastMessageEndForGeoposition2.setDescriptionEmoji(weatherEmoji.getSunnyPars());
                                break;}
                            case "небольшой снег" -> {resultForecastMessageEndForGeoposition2.setDescriptionEmoji(weatherEmoji.getCloudSnowPars());
                                break;}
                            case "небольшой дождь" -> {resultForecastMessageEndForGeoposition2.setDescriptionEmoji(weatherEmoji.getCloudRainPars());
                                break;}
                            case "снег" -> {resultForecastMessageEndForGeoposition2.setDescriptionEmoji(weatherEmoji.getSnowflakePars());
                                break;
                            }
                        }
                    forecastMessageListEndForGeoposition.add(resultForecastMessageEndForGeoposition2);
                }
            }
            else {
            dayOfWeek = simpleDateFormat2.format(dt);
            tmpMax = Collections.max(entry.getValue()
                    .stream()
                    .map(r -> r.getTempMaximum())
                    .collect(Collectors.toList()));
            tmpMin = Collections.min(entry.getValue()
                    .stream()
                    .map(r -> r.getTempMinimum())
                    .collect(Collectors.toList()));
            humid = Collections.max(entry.getValue()
                    .stream()
                    .map(r -> r.getHumidity())
                    .collect(Collectors.toList()));
            
            resultForecastMessageEndForGeoposition.setDate(dayOfWeek);
            resultForecastMessageEndForGeoposition.setDayOfWeek(dayOfWeek);
            resultForecastMessageEndForGeoposition.setTempMaximum(tmpMax);
            resultForecastMessageEndForGeoposition.setTempMinimum(tmpMin);
            resultForecastMessageEndForGeoposition.setHumidity(humid);
            for (int j = 0; j < entry.getValue().size(); j++) {
                switch (entry.getValue().get(j).getDate().substring(11, 16)) {
                    case "12:00" -> {
                        resultForecastMessageEndForGeoposition.setDescriptionOfDay(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfDay(weatherEmoji.getCloudPars());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfDay(weatherEmoji.getWhiteSunBehindCloudPars());
                            break;}
                            case "пасмурно" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfDay(weatherEmoji.getCloudPars());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfDay(weatherEmoji.getWhiteSunSmallCloudPars());
                            break;}
                            case "ясно" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfDay(weatherEmoji.getSunnyPars());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfDay(weatherEmoji.getCloudSnowPars());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfDay(weatherEmoji.getCloudRainPars());
                            break;}
                            case "снег" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfDay(weatherEmoji.getSnowflakePars());
                            break;
                            }
                        }
                    }
                    case "00:00" -> {
                        resultForecastMessageEndForGeoposition.setDescriptionOfNight(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfNight(weatherEmoji.getCloudPars());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfNight(weatherEmoji.getWhiteSunBehindCloudPars());
                            break;}
                            case "пасмурно" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfNight(weatherEmoji.getCloudPars());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfNight(weatherEmoji.getWhiteSunSmallCloudPars());
                            break;}
                            case "ясно" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfNight(weatherEmoji.getCrescentMoonPars());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfNight(weatherEmoji.getCloudSnowPars());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfNight(weatherEmoji.getCloudRainPars());
                            break;}
                            case "снег" -> {resultForecastMessageEndForGeoposition.setDescriptionEmojiOfNight(weatherEmoji.getSnowflakePars());
                            break;}
                        }
                    }
                    default -> {
                        resultForecastMessageEndForGeoposition.setDescription(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageEndForGeoposition.setDescriptionEmoji(weatherEmoji.getCloudPars());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageEndForGeoposition.setDescriptionEmoji(weatherEmoji.getWhiteSunBehindCloudPars());
                            break;}
                            case "пасмурно" -> {resultForecastMessageEndForGeoposition.setDescriptionEmoji(weatherEmoji.getCloudPars());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageEndForGeoposition.setDescriptionEmoji(weatherEmoji.getWhiteSunSmallCloudPars());
                            break;}
                            case "ясно" -> {resultForecastMessageEndForGeoposition.setDescriptionEmoji(weatherEmoji.getCrescentMoonPars());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageEndForGeoposition.setDescriptionEmoji(weatherEmoji.getCloudSnowPars());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageEndForGeoposition.setDescriptionEmoji(weatherEmoji.getCloudRainPars());
                            break;}
                            case "снег" -> {resultForecastMessageEndForGeoposition.setDescriptionEmoji(weatherEmoji.getSnowflakePars());
                            break;}
                        }
                    }
                }
            }
            forecastMessageListEndForGeoposition.add(resultForecastMessageEndForGeoposition);
            }
        }
        return forecastMessageListEndForGeoposition;
    }
}
