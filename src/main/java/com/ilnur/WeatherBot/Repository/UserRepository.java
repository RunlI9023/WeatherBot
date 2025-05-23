package com.ilnur.WeatherBot.Repository;

import com.ilnur.WeatherBot.Entities.BotUser;
import com.ilnur.WeatherBot.Entities.City;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface UserRepository extends CrudRepository<BotUser, Long> {
    Boolean existsByBotUserId(Long id);
    BotUser findByBotUserId(Long id);
    List<City> findCityByBotUserId(Long id);
}
