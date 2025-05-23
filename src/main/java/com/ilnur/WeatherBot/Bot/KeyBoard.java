package com.ilnur.WeatherBot.Bot;

import com.ilnur.WeatherBot.Entities.BotUser;
import com.ilnur.WeatherBot.Entities.City;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class KeyBoard extends ReplyKeyboardMarkup {

    public KeyBoard() {
    }
    
    public ReplyKeyboardMarkup geoLocationReplyKeyboard(BotUser user) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow cityRowFirst = new KeyboardRow();
        KeyboardButton geoCurrentWeatherButton = new KeyboardButton();
        KeyboardButton cityNameButtonForFirstRow;
        geoCurrentWeatherButton.setRequestLocation(true);
        geoCurrentWeatherButton.setText("Отправить геопозицию");
        row.add(geoCurrentWeatherButton);
        keyboard.add(row);
        keyboard.add(cityRowFirst);
        List<City> top3 = user.getBotFindCityList()
                .stream()
                .sorted(Comparator.comparingInt(c -> c.getCityFindCount())).collect(Collectors.toList()).reversed();
        for (City c : top3) {
            cityNameButtonForFirstRow = new KeyboardButton();
            cityNameButtonForFirstRow.setText(c.getCityName());
            cityRowFirst.add(cityNameButtonForFirstRow);
        }
        
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
            return keyboardMarkup;
    }
    
}
