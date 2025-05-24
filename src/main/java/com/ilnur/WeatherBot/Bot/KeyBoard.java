package com.ilnur.WeatherBot.Bot;

import com.ilnur.WeatherBot.Entities.BotUser;
import com.ilnur.WeatherBot.Entities.City;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class KeyBoard extends ReplyKeyboardMarkup {
    
    private static final Logger logger = Logger.getLogger(KeyBoard.class.getName());

    public KeyBoard() {
    }
    
    public ReplyKeyboardMarkup geoLocationReplyKeyboard(BotUser user) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow cityRow = new KeyboardRow();
        KeyboardButton geoCurrentWeatherButton = new KeyboardButton();
        KeyboardButton cityNameButtonForFirstRow;
        geoCurrentWeatherButton.setRequestLocation(true);
        geoCurrentWeatherButton.setText("Отправить геопозицию");
        row.add(geoCurrentWeatherButton);
        keyboard.add(row);
        keyboard.add(cityRow);
        List<City> top3 = user
            .getBotFindCityList()
            .stream()
            .sorted(Comparator.comparingInt(count -> count.getCityFindCount()))
            .collect(Collectors.toList())
            .reversed();
        for (City c : top3) {
            if (cityRow.size() <= 3) {
                cityNameButtonForFirstRow = new KeyboardButton();
                cityNameButtonForFirstRow.setText(c.getCityName());
                cityRow.add(cityNameButtonForFirstRow);
            }
            else {
                logger.log(Level.INFO, "Кнопки заполнены");
            }
        }
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
            return keyboardMarkup;
    }
    
}
