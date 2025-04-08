
package com.ilnur.BenderWeatherAssistBot.Bot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Component
@Entity
public class BenderBotUser extends User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String botUserName;
    private Long botUserId;
    private List<String> botFindCityList = new ArrayList<>();

    public BenderBotUser() {
    }

    public BenderBotUser(long id, String botUserName, Long botUserId, String botFindCity, List<String> botFindCityList) {
        this.id = id;
        this.botUserName = botUserName;
        this.botUserId = botUserId;
        this.botFindCityList = botFindCityList;
    }

    public BenderBotUser(String botUserName, Long botUserId) {
        this.botUserName = botUserName;
        this.botUserId = botUserId;
    }
    
    

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getBotUserName() {
        return botUserName;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public Long getBotUserId() {
        return botUserId;
    }

    public void setBotUserId(Long botUserId) {
        this.botUserId = botUserId;
    }

    public List<String> getBotFindCityList() {
        return botFindCityList;
    }

    public void setBotFindCityList(List<String> botFindCityList) {
        this.botFindCityList = botFindCityList;
    }
}
