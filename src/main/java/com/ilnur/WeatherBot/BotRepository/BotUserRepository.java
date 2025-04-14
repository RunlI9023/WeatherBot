package com.ilnur.WeatherBot.BotRepository;

import com.ilnur.WeatherBot.Bot.BotUser;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotUserRepository extends CrudRepository<BotUser, Long> {
    Boolean existsByBotUserId(Long botUserId);
}
