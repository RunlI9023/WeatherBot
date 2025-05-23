package com.ilnur.WeatherBot.Bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilnur.WeatherBot.RestClient.BotRestClient;
import com.ilnur.WeatherBot.ForecastForGeoPosition.ForecastForGeoposition;
import com.ilnur.WeatherBot.ForecastForCityName.ForecastForCityName;
import com.ilnur.WeatherBot.ForecastForCityName.ForecastObjectForGrouping;
import com.ilnur.WeatherBot.ForecastForCityName.ResultForecastObjectForTGMessageCity;
import com.ilnur.WeatherBot.ForecastForGeoPosition.ResultForecastObjectForTGMessageGeoposition;
import com.ilnur.WeatherBot.ForecastForGeoPosition.ForecastObjectForGroupingGeoposition;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class MessageGenerator {
    
    private ForecastForCityName forecastForCityName;
    private ForecastForGeoposition forecastFoGeoposition;
    private BotRestClient restClient;
    private WeatherEmoji weatherEmoji;
    //private ReplyKeyboardMarkup geoLocationReplyKeyboard;
    
    private final Double pressureConst = 0.750062;
    
    private Double maxTemperatureForCityName;
    private Double minTemperatureForCityName;
    private Integer humidityForCityName;
    private Integer maxPressureForCityName;
    
    private Double maxTemperatureForGeoposition;
    private Double minTemperatureForGeoposition;
    private Integer humidityForGeoposition;
    
    private final DateTimeFormatter formatForDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatForFullDate = DateTimeFormatter.ofPattern("d MMMM yyyy г.", Locale.of("ru", "RU"));
    private final DateTimeFormatter formatForDayOfWeek = DateTimeFormatter.ofPattern("EEEE", Locale.of("ru", "RU"));
    private final DateTimeFormatter formatForHours = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter formatForHoursShort = DateTimeFormatter.ofPattern("HH:mm");
    private final LocalDate dateToday = LocalDate.now();
    private final LocalTime timeToday = LocalTime.now();
    private LocalDate dateForForecastObjectCityName;
    private LocalTime timeForForecastObjectCityName;
    private String dayOfWeekForForecastObjectCityName;
    private String fullDateForForecastObjectCityName;

    private LocalDate dateForForecastObjectGeoposition;
    private LocalTime timeForForecastObjectGeoposition;
    private String dayOfWeekForForecastObjectGeoposition;
    private String fullDateForForecastObjectGeoposition;
    private final String dayOfWeekForCompareTodayAndNotToday = dateToday.format(formatForDayOfWeek);
    
    private String cityNameForDB;
    private final String dateForCurrentWeatherTextCity = dateToday.format(formatForFullDate);
    private final String hoursForCurrentWeatherTextCity = timeToday.format(formatForHoursShort);
    private String currentWeatherTextCity;
    
    private String weatherTextForMessageGeo = "";
    private String bufferTextForMessageGeo;
    private final String dateForCurrentWeatherTextGeo = dateToday.format(formatForFullDate);
    private final String hoursForCurrentWeatherTextGeo = timeToday.format(formatForHoursShort);
    private String currentWeatherTextGeo;
    
    private final String tgSendMessageFormatForHours = "%s: %-3s%+3d%-3s%2s%-3s%-3s%d%%\n";
    private final String tgSendMessageFormatForDays = "\n%s, %s:\n%-3s%+3d%-2s   %-3s%+3d%-2s%4s%3s%-3s%d%%\n";
    private final String tgSendMessageFormatForCurrentWeatherNow = "Почасовой прогноз на сегодня, \n%s:\n\n";
    

    public MessageGenerator() {
    }

    @Autowired
    public MessageGenerator(BotRestClient restClient, WeatherEmoji weatherEmoji/*, ReplyKeyboardMarkup geoLocationReplyKeyboard*/) {
        this.restClient = restClient;
        this.weatherEmoji = weatherEmoji;
        //this.geoLocationReplyKeyboard = geoLocationReplyKeyboard;
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

//    public SendMessage requestGeoPosition(Long who) {
//        SendMessage requestGeoPositionMessage = SendMessage.builder()
//                .chatId(who.toString())
//                .replyMarkup(geoLocationReplyKeyboard)
//                .text("Для того, чтобы узнать погоду, "
//                        + "введи название нужного города или нажми на кнопку "
//                        + "в меню для получения погоды по текущей геолокации")
//                .build();
//        return requestGeoPositionMessage;
//    }
    
    public SendMessage cityNotFound(Long who) {
        SendMessage cityNotFoundMessage = SendMessage.builder()
                .chatId(who.toString())
                .text("Город не найден. Проверь корректность ввода и повтори попытку.")
                .build();
        return cityNotFoundMessage;
    }
    
    public SendMessage weatherForecastForGeoposition(Long who) throws JsonProcessingException, ParseException, InterruptedException, ExecutionException {
        forecastFoGeoposition = restClient.getWeatherForecastForGeoposition(
                     restClient.getGeoLatitude().toString(), 
                     restClient.getGeoLongitude().toString()).get();
        restClient.getWeatherForecastForGeoposition(restClient.getGeoLatitude().toString(), 
                     restClient.getGeoLongitude().toString()).complete(forecastFoGeoposition);
        
        String weatherEmojiForGeoposition = "";
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
            if (getResultForecastObjectsForGeoposition().get(i).getDayOfWeek().equals(dayOfWeekForCompareTodayAndNotToday)){                
            textInForGeoposition = "\n" +    
                getResultForecastObjectsForGeoposition().get(i).getHours() + ":" + textInTabOne +
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmoji() + textInTabTwo +
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMaximum()) + "\u2103" + textInTabThree + 
                weatherEmoji.getHumidity() + textInTabFour + getResultForecastObjectsForGeoposition().get(i).getHumidity() + "%"
                ;
                textOutForGeoposition += textInForGeoposition;
            }     
            else {
            textInForGeoposition = "\n" +    
                getResultForecastObjectsForGeoposition().get(i).getDayOfWeek() + ", " + 
                getResultForecastObjectsForGeoposition().get(i).getDate() + ": " + "\n" +
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmojiOfDay() + "  " +
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmojiOfNight() + "  " +
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMaximum()) + "\u2103" + "  " + 
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMinimum()) + "\u2103" + "  " +
                weatherEmoji.getHumidity() + " " + getResultForecastObjectsForGeoposition().get(i).getHumidity() + "%";
                textOutForGeoposition += textInForGeoposition;
            } 
        }
        
        SendMessage weatherForecastForGeopositionMessage = SendMessage.builder()
                .chatId(who.toString())
                .text(textOutForGeoposition)
                .build();
        return weatherForecastForGeopositionMessage;
    }
    
    public SendMessage weatherForecastForCityName(Long who, String city) throws JsonProcessingException, ParseException, InterruptedException, ExecutionException {
        forecastForCityName = restClient.getWeatherForecastForCityName(city).get();
        restClient.getWeatherForecastForCityName(city).complete(forecastForCityName);
        cityNameForDB = forecastForCityName.getCity().toString();
        String bufferTextForHoursMessageCity;
        String bufferTextForDaysMessageCity;
        //geoLocationReplyKeyboard.getKeyboard().add(new KeyboardRow().add(e));
        //System.out.println(forecastForCityName.getCity().getSunrise() + " : " + forecastForCityName.getCity().getSunset());
        currentWeatherTextCity = String.format(tgSendMessageFormatForCurrentWeatherNow,dateForCurrentWeatherTextCity);
        String weatherTextForMessageCity = "";
        for (int i = 0; i < getResultForecastObjectsForCityName().size(); i++) {
            if (getResultForecastObjectsForCityName().get(i).getDayOfWeek().equals(dayOfWeekForCompareTodayAndNotToday)){
            bufferTextForHoursMessageCity = String.format(tgSendMessageFormatForHours,
                getResultForecastObjectsForCityName().get(i).getHours(),
                getResultForecastObjectsForCityName().get(i).getDescriptionEmoji(),
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMaximum()),
                "\u2103",
                "\u2913\u2913",//U+23F2weatherEmoji.getSmallRedTriangleDown(), //"\u21ca",//weatherEmoji.getExclamation(),
                getResultForecastObjectsForCityName().get(i).getPressure(),
                weatherEmoji.getHumidity(),
                getResultForecastObjectsForCityName().get(i).getHumidity());
                    weatherTextForMessageCity += bufferTextForHoursMessageCity;
            }       
            else {
            bufferTextForDaysMessageCity = String.format(tgSendMessageFormatForDays,    
                getResultForecastObjectsForCityName().get(i).getDayOfWeek(),
                getResultForecastObjectsForCityName().get(i).getDate(),
                getResultForecastObjectsForCityName().get(i).getDescriptionEmojiOfDay(),
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMaximum()),
                "\u2103",
                getResultForecastObjectsForCityName().get(i).getDescriptionEmojiOfNight(),
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMinimum()),
                "\u2103",
                "\u2913\u2913",//weatherEmoji.getSmallRedTriangleDown(),
                getResultForecastObjectsForCityName().get(i).getPressure(),
                weatherEmoji.getHumidity(),
                getResultForecastObjectsForCityName().get(i).getHumidity());
                    weatherTextForMessageCity += bufferTextForDaysMessageCity;
            } 
        }

        SendMessage weatherForecastForCityNameMessage = SendMessage.builder()
                .chatId(who.toString())
                .text(currentWeatherTextCity + weatherTextForMessageCity)
                .build();
        return weatherForecastForCityNameMessage;
    }
    
    public List<ResultForecastObjectForTGMessageCity> getResultForecastObjectsForCityName() throws ParseException {
        List<ResultForecastObjectForTGMessageCity> resultForecastMessageCity = new ArrayList<>();
        List<ResultForecastObjectForTGMessageCity> resultForecastMessageCityForHours = new ArrayList<>();
        for (Map.Entry<String, List<ForecastObjectForGrouping>> entry : forecastForCityName.resultForecastMessage().entrySet()) {
            ResultForecastObjectForTGMessageCity resultForecastMessageForDayCity = new ResultForecastObjectForTGMessageCity();
            dateForForecastObjectCityName = LocalDate.parse(entry.getKey(), formatForDate);
            dayOfWeekForForecastObjectCityName = dateForForecastObjectCityName.format(formatForDayOfWeek);
            if (dateToday.toString().equals(entry.getKey().substring(0, 10))) {
                for (int i = 0; i < entry.getValue().size() ; i++) {
                    ResultForecastObjectForTGMessageCity resultForecastMessageForHoursCity = new ResultForecastObjectForTGMessageCity();
                    resultForecastMessageForHoursCity.setHours(entry.getValue().get(i).getDate().substring(11, 16) + " ч");
                    resultForecastMessageForHoursCity.setDayOfWeek(dayOfWeekForForecastObjectCityName);
                    resultForecastMessageForHoursCity.setTempMaximum(entry.getValue().get(i).getTempMaximum());
                    resultForecastMessageForHoursCity.setHumidity(entry.getValue().get(i).getHumidity());
                    resultForecastMessageForHoursCity.setPressure((int)((entry.getValue().get(i).getPressure()) * pressureConst));
                    resultForecastMessageForHoursCity.setDescription(entry.getValue().get(i).getDescription());
                        switch(entry.getValue().get(i).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getCloud());
                                break;}
                            case "облачно с прояснениями" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getWhiteSunBehindCloud());
                                break;}
                            case "пасмурно" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getCloud());
                                break;}
                            case "небольшая облачность" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getWhiteSunSmallCloud());
                                break;}
                            case "ясно" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getSunny());
                                break;}
                            case "небольшой снег" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getCloudSnow());
                                break;}
                            case "небольшой дождь" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getCloudRain());
                                break;}
                            case "дождь" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getUmbrella());
                                break;}
                            case "снег" -> {resultForecastMessageForHoursCity.setDescriptionEmoji(weatherEmoji.getSnowflake());
                                break;
                            }
                        }
                    resultForecastMessageCityForHours.add(resultForecastMessageForHoursCity);
                }
            }
            else {
            fullDateForForecastObjectCityName = dateForForecastObjectCityName.format(formatForFullDate);
            maxTemperatureForCityName = Collections.max(entry.getValue()
                .stream()
                .map(r -> r.getTempMaximum())
                .collect(Collectors.toList()));
            minTemperatureForCityName = Collections.min(entry.getValue()
                .stream()
                .map(r -> r.getTempMinimum())
                .collect(Collectors.toList()));
            humidityForCityName = Collections.max(entry.getValue()
                .stream()
                .map(r -> r.getHumidity())
                .collect(Collectors.toList()));
            
            maxPressureForCityName = Collections.max(entry.getValue()
                .stream()
                .map(r -> r.getPressure())
                .collect(Collectors.toList()));
            
            resultForecastMessageForDayCity.setDate(fullDateForForecastObjectCityName);
            resultForecastMessageForDayCity.setDayOfWeek(dayOfWeekForForecastObjectCityName);
            resultForecastMessageForDayCity.setTempMaximum(maxTemperatureForCityName);
            resultForecastMessageForDayCity.setTempMinimum(minTemperatureForCityName);
            resultForecastMessageForDayCity.setHumidity(humidityForCityName);
            resultForecastMessageForDayCity.setPressure((int)(pressureConst *  maxPressureForCityName));
            for (int j = 0; j < entry.getValue().size(); j++) {
                switch (entry.getValue().get(j).getDate().substring(11, 16)) {
                    case "12:00" -> {
                        resultForecastMessageForDayCity.setDescriptionOfDay(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getCloud());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getWhiteSunBehindCloud());
                            break;}
                            case "пасмурно" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getCloud());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getWhiteSunSmallCloud());
                            break;}
                            case "ясно" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getSunny());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getCloudSnow());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getCloudRain());//getCloudRain()
                            break;}
                            case "дождь" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getUmbrella());
                            break;}
                            case "снег" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfDay(weatherEmoji.getSnowflake());
                            break;
                            }
                        }
                    }
                    case "00:00" -> {
                        resultForecastMessageForDayCity.setDescriptionOfNight(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getCloud());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getWhiteSunBehindCloud());
                            break;}
                            case "пасмурно" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getCloud());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getWhiteSunSmallCloud());
                            break;}
                            case "ясно" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getCrescentMoon());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getCloudSnow());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getCloudRain());
                            break;}
                            case "дождь" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getUmbrella());
                            break;}
                            case "снег" -> {resultForecastMessageForDayCity.setDescriptionEmojiOfNight(weatherEmoji.getSnowflake());
                            break;}
                        }
                    }
                }
            }
            resultForecastMessageCity.add(resultForecastMessageForDayCity);
            
            }
        }
        resultForecastMessageCity.addAll(0, resultForecastMessageCityForHours);
        return resultForecastMessageCity;
    }
    
    public List<ResultForecastObjectForTGMessageGeoposition> getResultForecastObjectsForGeoposition() throws ParseException {
        List<ResultForecastObjectForTGMessageGeoposition> resultForecastMessageGeo = new ArrayList<>();
        
        for (Map.Entry<String, List<ForecastObjectForGroupingGeoposition>> entry : forecastFoGeoposition.resultForecastMessageForGeoposition().entrySet()) {
            ResultForecastObjectForTGMessageGeoposition resultForecastMessageForDayGeo = new ResultForecastObjectForTGMessageGeoposition();
            dateForForecastObjectGeoposition = LocalDate.parse(entry.getKey(), formatForDate);
            dayOfWeekForForecastObjectGeoposition = dateForForecastObjectGeoposition.format(formatForDayOfWeek);
            if (dateToday.toString().equals(entry.getKey().substring(0, 10))) {
                for (int i = 0; i < entry.getValue().size() ; i++) {
                    ResultForecastObjectForTGMessageGeoposition resultForecastMessageForHoursGeo = new ResultForecastObjectForTGMessageGeoposition();
                    resultForecastMessageForHoursGeo.setHours(entry.getValue().get(i).getDate().substring(11, 16) + " ч");
                    resultForecastMessageForHoursGeo.setDayOfWeek(dayOfWeekForForecastObjectGeoposition);
                    resultForecastMessageForHoursGeo.setTempMaximum(entry.getValue().get(i).getTempMaximum());
                    resultForecastMessageForHoursGeo.setHumidity(entry.getValue().get(i).getHumidity());
                    resultForecastMessageForHoursGeo.setDescription(entry.getValue().get(i).getDescription());
                        switch(entry.getValue().get(i).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getCloud());
                                break;}
                            case "облачно с прояснениями" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getWhiteSunBehindCloud());
                                break;}
                            case "пасмурно" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getCloud());
                                break;}
                            case "небольшая облачность" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getWhiteSunSmallCloud());
                                break;}
                            case "ясно" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getSunny());
                                break;}
                            case "небольшой снег" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getCloudSnow());
                                break;}
                            case "небольшой дождь" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getCloudRain());
                                break;}
                            case "дождь" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getCloudRain());
                                break;}
                            case "снег" -> {resultForecastMessageForHoursGeo.setDescriptionEmoji(weatherEmoji.getSnowflake());
                                break;
                            }
                        }
                    resultForecastMessageGeo.add(resultForecastMessageForHoursGeo);
                }
            }
            else {
            
            fullDateForForecastObjectGeoposition = dateForForecastObjectGeoposition.format(formatForFullDate);
            maxTemperatureForGeoposition = Collections.max(entry.getValue()
                .stream()
                .map(r -> r.getTempMaximum())
                .collect(Collectors.toList()));
            minTemperatureForGeoposition = Collections.min(entry.getValue()
                .stream()
                .map(r -> r.getTempMinimum())
                .collect(Collectors.toList()));
            humidityForGeoposition = Collections.max(entry.getValue()
                .stream()
                .map(r -> r.getHumidity())
                .collect(Collectors.toList()));
            
            resultForecastMessageForDayGeo.setDate(fullDateForForecastObjectGeoposition);
            resultForecastMessageForDayGeo.setDayOfWeek(dayOfWeekForForecastObjectGeoposition);
            resultForecastMessageForDayGeo.setTempMaximum(maxTemperatureForGeoposition);
            resultForecastMessageForDayGeo.setTempMinimum(minTemperatureForGeoposition);
            resultForecastMessageForDayGeo.setHumidity(humidityForGeoposition);
            for (int j = 0; j < entry.getValue().size(); j++) {
                switch (entry.getValue().get(j).getDate().substring(11, 16)) {
                    case "12:00" -> {
                        resultForecastMessageForDayGeo.setDescriptionOfDay(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getCloud());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getWhiteSunBehindCloud());
                            break;}
                            case "пасмурно" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getCloud());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getWhiteSunSmallCloud());
                            break;}
                            case "ясно" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getSunny());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getCloudSnow());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getCloudRain());
                            break;}
                            case "дождь" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getCloudRain());
                            break;}
                            case "снег" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfDay(weatherEmoji.getSnowflake());
                            break;
                            }
                        }
                    }
                    case "00:00" -> {
                        resultForecastMessageForDayGeo.setDescriptionOfNight(entry.getValue().get(j).getDescription());
                        switch(entry.getValue().get(j).getDescription()) {
                            case "переменная облачность" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getCloud());
                            break;}
                            case "облачно с прояснениями" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getWhiteSunBehindCloud());
                            break;}
                            case "пасмурно" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getCloud());
                            break;}
                            case "небольшая облачность" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getWhiteSunSmallCloud());
                            break;}
                            case "ясно" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getCrescentMoon());
                            break;}
                            case "небольшой снег" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getCloudSnow());
                            break;}
                            case "небольшой дождь" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getCloudRain());
                            break;}
                            case "дождь" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getCloudRain());
                            break;}
                            case "снег" -> {resultForecastMessageForDayGeo.setDescriptionEmojiOfNight(weatherEmoji.getSnowflake());
                            break;}
                        }
                    }
                }
            }
            resultForecastMessageGeo.add(resultForecastMessageForDayGeo);
            }
        }
        return resultForecastMessageGeo;
    }

    public String getCityNameForDB() {
        return cityNameForDB;
    }

    public void setCityNameForDB(String cityNameForDB) {
        this.cityNameForDB = cityNameForDB;
    }
    
    
}
