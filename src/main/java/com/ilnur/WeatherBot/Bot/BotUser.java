
package com.ilnur.WeatherBot.Bot;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Component
@Entity
@Table(name = "bot_users")
public class BotUser extends User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "tg_id")
    private Long botUserId;
    @Column(name = "user_name")
    private String botUserName;
    @OneToMany
    @JoinColumn(name = "tg_id")
    private BotUserCity botFindCityList;

    public BotUser() {
    }

    public BotUser(long id, String botUserName, Long botUserId, String botFindCity, BotUserCity botFindCityList) {
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

    public BotUserCity getBotFindCityList() {
        return botFindCityList;
    }

    public void setBotFindCityList(BotUserCity botFindCityList) {
        this.botFindCityList = botFindCityList;
    }
}
