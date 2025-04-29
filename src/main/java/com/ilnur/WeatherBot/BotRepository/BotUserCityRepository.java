package com.ilnur.WeatherBot.BotRepository;

import com.ilnur.WeatherBot.Bot.BotUserCity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface BotUserCityRepository extends CrudRepository<BotUserCity, Long> {
    
}
