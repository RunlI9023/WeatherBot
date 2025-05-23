package com.ilnur.WeatherBot.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users_city")
public class City {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "city_id")
    private Long id;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "city_count")
    private int cityFindCount;

    public City() {
    }

    public City(String cityName, int cityFindCount) {
        this.cityName = cityName;
        this.cityFindCount = cityFindCount;
    }
    
    public City(String cityName) {
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityFindCount() {
        return cityFindCount;
    }

    public void setCityFindCount(int cityFindCount) {
        this.cityFindCount = cityFindCount;
    }
    
    public void cityFindCountIncrement(int i) {
        this.cityFindCount += i;
    }
}
