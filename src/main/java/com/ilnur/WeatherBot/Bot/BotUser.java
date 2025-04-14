
package com.ilnur.WeatherBot.Bot;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Component
@Entity
@Table(name = "weather_bot_users")
public class BotUser extends User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;
    @Column(name = "bot_user_ID")
    private Long botUserId;
    @Column(name = "bot_user_name")
    private String botUserName;
    @ElementCollection
    @Column(name = "bot_user_city")
    private List<String> botFindCityList = new ArrayList<>();

    public BotUser() {
    }

    public BotUser(long id, String botUserName, Long botUserId, String botFindCity, List<String> botFindCityList) {
        this.id = id;
        this.botUserName = botUserName;
        this.botUserId = botUserId;
        this.botFindCityList = botFindCityList;
    }

    public BotUser(String botUserName, Long botUserId) {
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
