package com.ilnur.WeatherBot.BotRepository;

import com.ilnur.WeatherBot.Bot.BotUserFindCity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotUserFindCityRepository extends CrudRepository<BotUserFindCity, Long> {
    Boolean existsByCityName(String city);
}
