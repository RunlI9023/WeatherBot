package com.ilnur.WeatherBot.Bot;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class KeyBoard extends ReplyKeyboardMarkup {

    public KeyBoard() {
    }
    
    public ReplyKeyboardMarkup geoLocationReplyKeyboard(CrudRepository<BotUserFindCity, Long> repo) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow cityRow = new KeyboardRow();
        KeyboardButton geoCurrentWeatherButton = new KeyboardButton();
        geoCurrentWeatherButton.setRequestLocation(true);
        geoCurrentWeatherButton.setText("Получить погоду по геолокации");
        row.add(geoCurrentWeatherButton);
        keyboard.add(row);
        keyboard.add(cityRow);
        for (BotUserFindCity c : repo.findAll()) {
//            int max1 = Integer.MIN_VALUE;
//            int max2 = Integer.MIN_VALUE;
//            int max3 = Integer.MIN_VALUE;
//            if (c.getCityFindCount() > max1)
//    {
//        max3 = max2; max2 = max1; max1 = c.getCityFindCount();
//    }
//    else if (c.getCityFindCount() > max2)
//    {
//        max3 = max2; max2 = c.getCityFindCount();
//    }
//    else if (c.getCityFindCount() > max3)
//    {
//        max3 = c.getCityFindCount();
//    }
            KeyboardButton cityNameButton = new KeyboardButton();
            cityNameButton.setText(c.getCityName());
            cityRow.add(cityNameButton);
//            if (cityRow.size() == 4) {
//                KeyboardRow cityRow2 = new KeyboardRow();
//                keyboard.add(cityRow2);
//                KeyboardButton cityNameButton2 = new KeyboardButton();
//                cityNameButton.setText(c.getCityName());
//                cityRow.add(cityNameButton2);
//            }
        }
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
            return keyboardMarkup;
        }
    
}
