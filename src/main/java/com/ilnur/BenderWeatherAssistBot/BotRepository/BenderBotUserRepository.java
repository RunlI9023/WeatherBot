package com.ilnur.BenderWeatherAssistBot.BotRepository;

import com.ilnur.BenderWeatherAssistBot.Bot.BenderBotUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface BenderBotUserRepository extends CrudRepository<BenderBotUser, Long> {
    
}
