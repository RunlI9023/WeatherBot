package com.ilnur.WeatherBot.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;

@Component
@Entity
@Table(name = "bot_users")
public class BotUser extends User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "chat_id")
    private Long botUserId;
    @Column(name = "user_name")
    private String botUserName;
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private List<City> botFindCityList = new ArrayList<>();

    public BotUser() {
    }
    
    public BotUser(String botUserName, Long botUserId, List<City> botFindCityList) {
        this.botUserName = botUserName;
        this.botUserId = botUserId;
        this.botFindCityList = botFindCityList;
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

    public List<City> getBotFindCityList() {
        return botFindCityList;
    }

    public void setBotFindCityList(List<City> botFindCityList) {
        this.botFindCityList = botFindCityList;
    }
}
