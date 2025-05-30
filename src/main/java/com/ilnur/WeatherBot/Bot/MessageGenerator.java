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

@Component
public class MessageGenerator {
    
    private ForecastForCityName forecastForCityName;
    private ForecastForGeoposition forecastFoGeoposition;
    private BotRestClient restClient;
    private WeatherEmoji weatherEmoji;
    
    private final DateTimeFormatter FORMAT_FOR_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter FORMAT_FOR_FULL_DATE = DateTimeFormatter.ofPattern("d MMMM yyyy г.", Locale.of("ru", "RU"));
    private final DateTimeFormatter FORMAT_FOR_DAY_OF_WEEK = DateTimeFormatter.ofPattern("EEEE", Locale.of("ru", "RU"));
    private final DateTimeFormatter FORMAT_FOR_HOURS_SHORT = DateTimeFormatter.ofPattern("HH:mm");
    private final String FORMAT_FOR_HOURS = "%s: %-3s%+3d%-3s%2s%-3s%-3s%d%%\n";
    private final String FORMAT_FOR_DAYS = "\n%s, %s:\n%-3s%+3d%-2s   %-3s%+3d%-2s%4s%3s%-3s%d%%\n";
    private final String FORMAT_FOR_CURRENT_WEATHER_CITY = "Погода в г. %s cегодня,\n%s:\n\n";
    private final String FORMAT_FOR_CURRENT_WEATHER_GEO = "Погода в г. %s cегодня,\n%s:\n\n";
    private final Double PRESSURE_RATIO = 0.750062;
    
    private LocalDate dateToday;
    private LocalTime timeToday;
    private LocalDate dateForForecastObjectGeoposition;
    private LocalDate dateForForecastObjectCityName;
    
    private Double maxTemperatureForCityName;
    private Double minTemperatureForCityName;
    private Integer humidityForCityName;
    private Integer maxPressureForCityName;
    private Double maxTemperatureForGeoposition;
    private Double minTemperatureForGeoposition;
    private Integer humidityForGeoposition;
    
    private String dayOfWeekForForecastObjectCityName;
    private String fullDateForForecastObjectCityName;
    private String dayOfWeekForForecastObjectGeoposition;
    private String fullDateForForecastObjectGeoposition;
    private String dayOfWeekForCompareTodayAndNotToday;
    private String cityNameForDB;
    private String dateForCurrentWeatherTextCity;
    private String dateForCurrentWeatherTextGeo;
    private String currentWeatherTextCity;
    private String currentWeatherTextGeo;

    public MessageGenerator() {
    }

    @Autowired
    public MessageGenerator(BotRestClient restClient, WeatherEmoji weatherEmoji) {
        this.restClient = restClient;
        this.weatherEmoji = weatherEmoji;
    }
    
    public SendMessage greetingUser(Long who, String userName) {
        SendMessage greetingUserMessage = SendMessage
                .builder()
                .chatId(who.toString())
                .text("Привет, " + userName + "!")
                .build();
        return greetingUserMessage;
    }

    public SendMessage forAdmin(Long who, String userName, long userId) {
        SendMessage forAdmin = SendMessage
                .builder()
                .chatId(who.toString())
                .text("Присоединился новый пользователь, имя: " + userName + ", ID: " + userId)
                .build();
        return forAdmin;
    }
    
        public SendMessage help(Long who) {
        String help = "Условные обозначения: " + "\n" + 
                weatherEmoji.getCheck() + " - атмосферное давление в норме, от 755 до 765 мм.рт.ст." + "\n" +
                weatherEmoji.getExclamation() + " - повышенное атмосферное давление" + "\n" +
                weatherEmoji.getGrey_exclamation() + " - пониженное атмосферное давление" + "\n" +
                weatherEmoji.getHumidity() + " - влажность воздуха" + "\n" +
                weatherEmoji.getSunny() + ", " + weatherEmoji.getCrescentMoon() + " - ясная погода" + "\n" +
                weatherEmoji.getCloudRain() + " - небольшой дождь, возьмите зонт!" + "\n" +
                weatherEmoji.getCloudSnow() + " - небольшой снег" + "\n" +
                weatherEmoji.getUmbrella() + " - возможен сильный дождь, возьмите зонт!" + "\n" +
                weatherEmoji.getSnowflake() + " - возможен сильный снегопад, будьте осторожны на дорогах!" + "\n" +
                weatherEmoji.getCloudLightning() + " - гроза" + "\n" +
                weatherEmoji.getFog() + " - туман" + "\n" +
                weatherEmoji.getCloud() + " - пасмурно" + "\n" +
                weatherEmoji.getPartlySunny() + " - облачно с прояснениями" + "\n" +
                weatherEmoji.getWhiteSunBehindCloud() + " - переменная облачность" + "\n" +
                weatherEmoji.getWhiteSunBehindCloudRain() + " - облачно с прояснениями, возможен дождь" + "\n" +
                weatherEmoji.getWhiteSunSmallCloud() + " - небольшая облачность";
        
        SendMessage helpMessage = SendMessage
                .builder()
                .chatId(who.toString())
                .text(help)
                .build();
        return helpMessage;
        }
    
    public SendMessage cityNotFound(Long who) {
        SendMessage cityNotFoundMessage = SendMessage
                .builder()
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
        
        dateToday = LocalDate.now();
        dateForCurrentWeatherTextGeo = dateToday.format(FORMAT_FOR_FULL_DATE);
        String weatherTextForMessageGeo = "";
        String weatherForHoursTextGeo;
        String weatherForWeekTextGeo;
        currentWeatherTextGeo = String.format(FORMAT_FOR_CURRENT_WEATHER_GEO,
                forecastFoGeoposition.getCity().getName(),
                dateForCurrentWeatherTextGeo);
        
        setCityNameForDB(forecastFoGeoposition.getCity().getName());
        
        weatherTextForMessageGeo += currentWeatherTextGeo;
        
        for (int i = 0; i < getResultForecastObjectsForGeoposition().size(); i++) {     
            if (getResultForecastObjectsForGeoposition().get(i).getDayOfWeek().equals(dayOfWeekForCompareTodayAndNotToday)){                
            weatherForHoursTextGeo = String.format(FORMAT_FOR_HOURS,
                getResultForecastObjectsForGeoposition().get(i).getHours(),
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmoji(),
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMaximum()),
                "\u2103",
                getResultForecastObjectsForGeoposition().get(i).getPressureEmoji(),
                getResultForecastObjectsForGeoposition().get(i).getPressure(),
                weatherEmoji.getHumidity(),
                getResultForecastObjectsForGeoposition().get(i).getHumidity());
                
                weatherTextForMessageGeo += weatherForHoursTextGeo;
            }     
            else {
            weatherForWeekTextGeo = String.format(FORMAT_FOR_DAYS,
                getResultForecastObjectsForGeoposition().get(i).getDayOfWeek(),
                getResultForecastObjectsForGeoposition().get(i).getDate(),
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmojiOfDay(),
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMaximum()),
                "\u2103",
                getResultForecastObjectsForGeoposition().get(i).getDescriptionEmojiOfNight(),
                Math.round(getResultForecastObjectsForGeoposition().get(i).getTempMinimum()),
                "\u2103",
                getResultForecastObjectsForGeoposition().get(i).getPressureEmoji(),
                getResultForecastObjectsForGeoposition().get(i).getPressure(),
                weatherEmoji.getHumidity(),
                getResultForecastObjectsForGeoposition().get(i).getHumidity());
            
                weatherTextForMessageGeo += weatherForWeekTextGeo;
            } 
        }
        
        SendMessage weatherForecastForGeopositionMessage = SendMessage
                .builder()
                .chatId(who.toString())
                .text(weatherTextForMessageGeo)
                .build();
        return weatherForecastForGeopositionMessage;
        
        
    }
    
    public SendMessage weatherForecastForCityName(Long who, String city) throws JsonProcessingException, ParseException, InterruptedException, ExecutionException {
        forecastForCityName = restClient.getWeatherForecastForCityName(city).get();
        restClient.getWeatherForecastForCityName(city).complete(forecastForCityName);
        cityNameForDB = forecastForCityName.getCity().toString();
        String bufferTextForHoursMessageCity;
        String bufferTextForDaysMessageCity;
        dateToday = LocalDate.now();
        dayOfWeekForCompareTodayAndNotToday = dateToday.format(FORMAT_FOR_DAY_OF_WEEK);
        dateForCurrentWeatherTextCity = dateToday.format(FORMAT_FOR_FULL_DATE);
        currentWeatherTextCity = String.format(FORMAT_FOR_CURRENT_WEATHER_CITY, forecastForCityName.getCity().getName(),dateForCurrentWeatherTextCity);
        String weatherTextForMessageCity = ""; 
        weatherTextForMessageCity += currentWeatherTextCity;
        for (int i = 0; i < getResultForecastObjectsForCityName().size(); i++) {
            if (getResultForecastObjectsForCityName().get(i).getDayOfWeek().equals(dayOfWeekForCompareTodayAndNotToday)){
            bufferTextForHoursMessageCity = String.format(FORMAT_FOR_HOURS,
                getResultForecastObjectsForCityName().get(i).getHours(),
                getResultForecastObjectsForCityName().get(i).getDescriptionEmoji(),
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMaximum()),
                "\u2103",
                getResultForecastObjectsForCityName().get(i).getPressureEmoji(),
                getResultForecastObjectsForCityName().get(i).getPressure(),
                weatherEmoji.getHumidity(),
                getResultForecastObjectsForCityName().get(i).getHumidity());
                    weatherTextForMessageCity += bufferTextForHoursMessageCity;
            }       
            else {
            bufferTextForDaysMessageCity = String.format(FORMAT_FOR_DAYS,    
                getResultForecastObjectsForCityName().get(i).getDayOfWeek(),
                getResultForecastObjectsForCityName().get(i).getDate(),
                getResultForecastObjectsForCityName().get(i).getDescriptionEmojiOfDay(),
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMaximum()),
                "\u2103",
                getResultForecastObjectsForCityName().get(i).getDescriptionEmojiOfNight(),
                Math.round(getResultForecastObjectsForCityName().get(i).getTempMinimum()),
                "\u2103",
                getResultForecastObjectsForCityName().get(i).getPressureEmoji(),
                getResultForecastObjectsForCityName().get(i).getPressure(),
                weatherEmoji.getHumidity(),
                getResultForecastObjectsForCityName().get(i).getHumidity());
                    weatherTextForMessageCity += bufferTextForDaysMessageCity;
            } 
        }

        SendMessage weatherForecastForCityNameMessage = SendMessage
                .builder()
                .chatId(who.toString())
                .text(weatherTextForMessageCity)
                .build();
        return weatherForecastForCityNameMessage;
    }
    
    public List<ResultForecastObjectForTGMessageCity> getResultForecastObjectsForCityName() throws ParseException {
        List<ResultForecastObjectForTGMessageCity> resultForecastMessageCity = new ArrayList<>();
        List<ResultForecastObjectForTGMessageCity> resultForecastMessageCityForHours = new ArrayList<>();
        for (Map.Entry<String, List<ForecastObjectForGrouping>> entry : forecastForCityName.resultForecastMessage().entrySet()) {
            ResultForecastObjectForTGMessageCity resultForecastMessageForDayCity = new ResultForecastObjectForTGMessageCity();
            dateForForecastObjectCityName = LocalDate.parse(entry.getKey(), FORMAT_FOR_DATE);
            dayOfWeekForForecastObjectCityName = dateForForecastObjectCityName.format(FORMAT_FOR_DAY_OF_WEEK);
            dateToday = LocalDate.now();
            timeToday = LocalTime.now();
            dayOfWeekForCompareTodayAndNotToday = dateToday.format(FORMAT_FOR_DAY_OF_WEEK);
            dateForCurrentWeatherTextCity = dateToday.format(FORMAT_FOR_FULL_DATE);
            timeToday.format(FORMAT_FOR_HOURS_SHORT);
            if (dateToday.toString().equals(entry.getKey().substring(0, 10))) {
                for (int i = 0; i < entry.getValue().size() ; i++) {
                    ResultForecastObjectForTGMessageCity resultForecastMessageForHoursCity = new ResultForecastObjectForTGMessageCity();
                    resultForecastMessageForHoursCity.setHours(entry.getValue().get(i).getDate().substring(11, 16) + " ч");
                    resultForecastMessageForHoursCity.setDayOfWeek(dayOfWeekForForecastObjectCityName);
                    resultForecastMessageForHoursCity.setTempMaximum(entry.getValue().get(i).getTempMaximum());
                    resultForecastMessageForHoursCity.setHumidity(entry.getValue().get(i).getHumidity());
                    resultForecastMessageForHoursCity.setPressure((int)((entry.getValue().get(i).getPressure()) * PRESSURE_RATIO));
                    if (resultForecastMessageForHoursCity.getPressure() >= 755 && resultForecastMessageForHoursCity.getPressure() <= 765) {
                        resultForecastMessageForHoursCity.setPressureEmoji(weatherEmoji.getCheck());
                    }
                    else if (resultForecastMessageForHoursCity.getPressure() > 765) {
                        resultForecastMessageForHoursCity.setPressureEmoji(weatherEmoji.getExclamation());
                    }
                    else if (resultForecastMessageForHoursCity.getPressure() < 755) {
                        resultForecastMessageForHoursCity.setPressureEmoji(weatherEmoji.getGrey_exclamation());
                    }
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
            fullDateForForecastObjectCityName = dateForForecastObjectCityName.format(FORMAT_FOR_FULL_DATE);
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
            resultForecastMessageForDayCity.setPressure((int)(PRESSURE_RATIO *  maxPressureForCityName));
            if (resultForecastMessageForDayCity.getPressure() >= 755 && resultForecastMessageForDayCity.getPressure() <= 765) {
                resultForecastMessageForDayCity.setPressureEmoji(weatherEmoji.getCheck());
            }
            else if (resultForecastMessageForDayCity.getPressure() > 765) {
                resultForecastMessageForDayCity.setPressureEmoji(weatherEmoji.getExclamation());
            }
            else if (resultForecastMessageForDayCity.getPressure() < 755) {
                resultForecastMessageForDayCity.setPressureEmoji(weatherEmoji.getGrey_exclamation());
            }
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
            dateForForecastObjectGeoposition = LocalDate.parse(entry.getKey(), FORMAT_FOR_DATE);
            dayOfWeekForForecastObjectGeoposition = dateForForecastObjectGeoposition.format(FORMAT_FOR_DAY_OF_WEEK);
            dateToday = LocalDate.now();
            timeToday = LocalTime.now();
            dayOfWeekForCompareTodayAndNotToday = dateToday.format(FORMAT_FOR_DAY_OF_WEEK);
            dateForCurrentWeatherTextGeo = dateToday.format(FORMAT_FOR_FULL_DATE);
            timeToday.format(FORMAT_FOR_HOURS_SHORT);
            if (dateToday.toString().equals(entry.getKey().substring(0, 10))) {
                for (int i = 0; i < entry.getValue().size() ; i++) {
                    ResultForecastObjectForTGMessageGeoposition resultForecastMessageForHoursGeo = new ResultForecastObjectForTGMessageGeoposition();
                    resultForecastMessageForHoursGeo.setHours(entry.getValue().get(i).getDate().substring(11, 16) + " ч");
                    resultForecastMessageForHoursGeo.setDayOfWeek(dayOfWeekForForecastObjectGeoposition);
                    resultForecastMessageForHoursGeo.setTempMaximum(entry.getValue().get(i).getTempMaximum());
                    resultForecastMessageForHoursGeo.setHumidity(entry.getValue().get(i).getHumidity());
                    resultForecastMessageForHoursGeo.setPressure((int)((entry.getValue().get(i).getPressure()) * PRESSURE_RATIO));
                    if (resultForecastMessageForHoursGeo.getPressure() >= 755 && resultForecastMessageForHoursGeo.getPressure() <= 765) {
                        resultForecastMessageForHoursGeo.setPressureEmoji(weatherEmoji.getCheck());
                    }
                    else if (resultForecastMessageForHoursGeo.getPressure() > 765) {
                        resultForecastMessageForHoursGeo.setPressureEmoji(weatherEmoji.getExclamation());
                    }
                    else if (resultForecastMessageForHoursGeo.getPressure() < 755) {
                        resultForecastMessageForHoursGeo.setPressureEmoji(weatherEmoji.getGrey_exclamation());
                    }
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
            
            fullDateForForecastObjectGeoposition = dateForForecastObjectGeoposition.format(FORMAT_FOR_FULL_DATE);
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
            
            Integer press = Collections.max(entry.getValue()
                .stream()
                .map(r -> r.getPressure())
                .collect(Collectors.toList()));
            
            resultForecastMessageForDayGeo.setDate(fullDateForForecastObjectGeoposition);
            resultForecastMessageForDayGeo.setDayOfWeek(dayOfWeekForForecastObjectGeoposition);
            resultForecastMessageForDayGeo.setTempMaximum(maxTemperatureForGeoposition);
            resultForecastMessageForDayGeo.setTempMinimum(minTemperatureForGeoposition);
            resultForecastMessageForDayGeo.setPressure((int)(PRESSURE_RATIO *  press));
            if (resultForecastMessageForDayGeo.getPressure() >= 755 && resultForecastMessageForDayGeo.getPressure() <= 765) {
                resultForecastMessageForDayGeo.setPressureEmoji(weatherEmoji.getCheck());
            }
            else if (resultForecastMessageForDayGeo.getPressure() > 765) {
                resultForecastMessageForDayGeo.setPressureEmoji(weatherEmoji.getExclamation());
            }
            else if (resultForecastMessageForDayGeo.getPressure() < 755) {
                resultForecastMessageForDayGeo.setPressureEmoji(weatherEmoji.getGrey_exclamation());
            }
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
