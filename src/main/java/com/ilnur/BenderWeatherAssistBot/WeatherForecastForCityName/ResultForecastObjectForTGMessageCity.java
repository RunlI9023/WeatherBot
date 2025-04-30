package com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName;

import com.ilnur.BenderWeatherAssistBot.Bot.BenderBotWeatherEmoji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultForecastObjectForTGMessageCity {
    
    private String Date;
    private String DayOfWeek;
    private String Hours;
    private String PressureEmoji;
    private String DescriptionEmoji;
    private String DescriptionEmojiOfDay;
    private String DescriptionEmojiOfNight;
    private String Description;
    private String DescriptionOfDay;
    private String DescriptionOfNight;
    private Double TempMaximum;
    private Double TempMinimum;
    private Double TempFeelsLike;
    private Integer Humidity;
    private Integer Pressure;
    private Integer Visibility;
    private Integer Clouds;
    private Double WindSpeed;

    public ResultForecastObjectForTGMessageCity() {}

    public ResultForecastObjectForTGMessageCity(String Date, String DescriptionEmoji, String DescriptionEmojiOfDay,
            String DescriptionEmojiOfNight, String DayOfWeek, String Description, String DescriptionOfDay,
            String DescriptionOfNight, Double TempMaximum, Double TempMinimum, Double TempFeelsLike,
            Integer Humidity, Integer Pressure, Integer Visibility, Integer Clouds, Double WindSpeed, String PressureEmoji) {
        this.Date = Date;
        this.DescriptionEmoji = DescriptionEmoji;
        this.DescriptionEmojiOfDay = DescriptionEmojiOfDay;
        this.DescriptionEmojiOfNight = DescriptionEmojiOfNight;
        this.DayOfWeek = DayOfWeek;
        this.Description = Description;
        this.DescriptionOfDay = DescriptionOfDay;
        this.DescriptionOfNight = DescriptionOfNight;
        this.TempMaximum = TempMaximum;
        this.TempMinimum = TempMinimum;
        this.TempFeelsLike = TempFeelsLike;
        this.Humidity = Humidity;
        this.Pressure = Pressure;
        this.Visibility = Visibility;
        this.Clouds = Clouds;
        this.WindSpeed = WindSpeed;
        this.PressureEmoji = PressureEmoji;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String Hours) {
        this.Hours = Hours;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Double getTempMaximum() {
        return TempMaximum;
    }

    public void setTempMaximum(Double TempMaximum) {
        this.TempMaximum = TempMaximum;
    }

    public Double getTempMinimum() {
        return TempMinimum;
    }

    public void setTempMinimum(Double TempMinimum) {
        this.TempMinimum = TempMinimum;
    }

    public Double getTempFeelsLike() {
        return TempFeelsLike;
    }

    public void setTempFeelsLike(Double TempFeelsLike) {
        this.TempFeelsLike = TempFeelsLike;
    }

    public Integer getHumidity() {
        return Humidity;
    }

    public void setHumidity(Integer Humidity) {
        this.Humidity = Humidity;
    }

    public Integer getPressure() {
        return Pressure;
    }

    public void setPressure(Integer Pressure) {
        this.Pressure = Pressure;
    }

    public Integer getVisibility() {
        return Visibility;
    }

    public void setVisibility(Integer Visibility) {
        this.Visibility = Visibility;
    }

    public Integer getClouds() {
        return Clouds;
    }

    public void setClouds(Integer Clouds) {
        this.Clouds = Clouds;
    }

    public Double getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(Double WindSpeed) {
        this.WindSpeed = WindSpeed;
    }
    
    
    public String getDescriptionEmoji() {
        return DescriptionEmoji;
    }

    public void setDescriptionEmoji(String DescriptionEmoji) {
        this.DescriptionEmoji = DescriptionEmoji;
    }
    
    public String getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(String DayOfWeek) {
        this.DayOfWeek = DayOfWeek;
    }
    
    public String getDescriptionOfDay() {
        return DescriptionOfDay;
    }

    public void setDescriptionOfDay(String DescriptionOfDay) {
        this.DescriptionOfDay = DescriptionOfDay;
    }

    public String getDescriptionOfNight() {
        return DescriptionOfNight;
    }

    public void setDescriptionOfNight(String DescriptionOfNight) {
        this.DescriptionOfNight = DescriptionOfNight;
    }

    public String getDescriptionEmojiOfDay() {
        return DescriptionEmojiOfDay;
    }

    public void setDescriptionEmojiOfDay(String DescriptionEmojiOfDay) {
        this.DescriptionEmojiOfDay = DescriptionEmojiOfDay;
    }

    public String getDescriptionEmojiOfNight() {
        return DescriptionEmojiOfNight;
    }

    public void setDescriptionEmojiOfNight(String DescriptionEmojiOfNight) {
        this.DescriptionEmojiOfNight = DescriptionEmojiOfNight;
    }

    public String getPressureEmoji() {
        return PressureEmoji;
    }

    public void setPressureEmoji(String PressureEmoji) {
        this.PressureEmoji = PressureEmoji;
    }
    
    
}
