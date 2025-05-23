package com.ilnur.WeatherBot.Service;

import com.ilnur.WeatherBot.Bot.KeyBoard;
import com.ilnur.WeatherBot.Entities.BotUser;
import com.ilnur.WeatherBot.Entities.City;
import org.springframework.stereotype.Service;
import com.ilnur.WeatherBot.Repository.UserCityRepository;
import com.ilnur.WeatherBot.Repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BotService {
    
    private final UserRepository userRepository;;
    private final UserCityRepository userCityRepository;
    private final KeyBoard keyBoard;
    private static final Logger logger = Logger.getLogger(BotService.class.getName());

    public BotService(UserRepository userRepository, UserCityRepository userCityRepository, KeyBoard keyBoard) {
        this.userRepository = userRepository;
        this.userCityRepository = userCityRepository;
        this.keyBoard = keyBoard;
    }
    
    public void saveNewUser(Long id, String name) {
        BotUser user = new BotUser();
        user.setBotUserId(id);
        user.setBotUserName(name);
        userRepository.save(user);
        logger.log(Level.INFO, "Добавлен новый пользователь, ID: {0}", user.getID());
    }
    
    public Iterable<BotUser> getUsers() {
        return userRepository.findAll();
    }
    
    public BotUser getBotUser(Long id) {
        BotUser user = userRepository.findById(id).orElseThrow(NoSuchElementException :: new);
        return user;
    }
    
    public BotUser getBotUserByTgId(Long id) {
        BotUser userTg = null;
        for (BotUser user : userRepository.findAll()) {
            if (user.getBotUserId().equals(id)) {
                userTg = user;
            }
        }
        return userTg;
    }
    
    public Boolean existUserById(Long id) {
        return userRepository.existsByBotUserId(id);
    }
    
    public Boolean existUserByTgId(Long id) {
        Boolean result = false;
        for (BotUser user : userRepository.findAll()) {
            if (user.getBotUserId().equals(id)) {
                result = true;
            }
        }
        return result;
    }
    
/**
 * проверяем наличие введенного города в БД для пользователя,
 * если есть, обновляем count (счет запросов), если нет, добавляем новый город
*/
    public void saveNewUserCity(Long id, String cityName) {
        for (BotUser user : userRepository.findAll()) {
            if(user.getBotUserId().equals(id)){
            logger.log(Level.INFO, "В БД найден существующий пользователь: {0}", user.getBotUserName());
            user.getBotFindCityList()
                    .stream()
                    .filter(p  -> p.getCityName().equals(cityName))
                    .findAny()
                    .ifPresentOrElse(с -> с.cityFindCountIncrement(1), () -> user.getBotFindCityList().add(new City(cityName)));
            userRepository.save(user);
            logger.log(Level.INFO, "Сохранен или обновлен город: {0}", cityName);
            }
        }
    }
    
    public KeyBoard getKeyBoard() {
        return keyBoard;
    }
}
