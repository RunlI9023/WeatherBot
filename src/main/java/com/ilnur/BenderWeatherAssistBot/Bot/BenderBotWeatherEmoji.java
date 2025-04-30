package com.ilnur.BenderWeatherAssistBot.Bot;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiManager;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class BenderBotWeatherEmoji {

    private String humidity = EmojiParser.parseToUnicode(":droplet:");//капля влажность
    private String thermometer = EmojiParser.parseToUnicode(":thermometer:");//термометр
    private String windBlowingFace = EmojiParser.parseToUnicode(":wind_blowing_face:");//лицо с ветром
    private String cloud = EmojiParser.parseToUnicode(":cloud:");//облако
    private String fog = EmojiParser.parseToUnicode(":fog:");//tuman
    private String thunderCloudRain = EmojiParser.parseToUnicode(":thunder_cloud_rain:");
    private String whiteSunSmallCloud = EmojiParser.parseToUnicode(":white_sun_small_cloud:");
    private String whiteSunBehindCloud = EmojiParser.parseToUnicode(":white_sun_behind_cloud:");
    private String whiteSunBehindCloudRain = EmojiParser.parseToUnicode(":white_sun_behind_cloud_rain:");
    private String cloudRain = EmojiParser.parseToUnicode(":cloud_rain:");
    private String cloudSnow = EmojiParser.parseToUnicode(":cloud_snow:");
    private String cloudLightning = EmojiParser.parseToUnicode(":cloud_lightning:");
    private String partlySunny = EmojiParser.parseToUnicode(":partly_sunny:");
    private String sunny = EmojiParser.parseToUnicode(":sunny:");
    private String snowflake = EmojiParser.parseToUnicode(":snowflake:");
    private String crescentMoon = EmojiParser.parseToUnicode(":crescent_moon:");
    private String umbrella = EmojiParser.parseToUnicode(":umbrella:");
    private String exclamation = EmojiParser.parseToUnicode(":exclamation:");
    private String smallRedTriangleDown = EmojiParser.parseToUnicode(":small_red_triangle_down:");
    private String clock12 = EmojiParser.parseToUnicode(":clock12:");
    private String check = EmojiParser.parseToUnicode(":white_check_mark:");
    private String grey_exclamation = EmojiParser.parseToUnicode(":grey_exclamation:");

    public BenderBotWeatherEmoji() {
    }
    
    public BenderBotWeatherEmoji(String humidity, String thermometer, String windBlowingFace, String cloud, 
            String fog, String thunderCloudRain, String whiteSunSmallCloud, String whiteSunBehindCloud, 
            String whiteSunBehindCloudRain, String cloudRain, String cloudSnow, String cloudLightning, String partlySunny, 
            String sunny, String snowflake, String crescentMoon, String umbrella, String exclamation, String smallRedTriangleDown,
            String clock12, String check, String grey_exclamation) {
        this.humidity = humidity;
        this.thermometer = thermometer;
        this.windBlowingFace = windBlowingFace;
        this.cloud = cloud;
        this.fog = fog;
        this.thunderCloudRain = thunderCloudRain;
        this.whiteSunSmallCloud = whiteSunSmallCloud;
        this.whiteSunBehindCloud = whiteSunBehindCloud;
        this.whiteSunBehindCloudRain = whiteSunBehindCloudRain;
        this.cloudRain = cloudRain;
        this.cloudSnow = cloudSnow;
        this.cloudLightning = cloudLightning;
        this.partlySunny = partlySunny;
        this.sunny = sunny;
        this.snowflake = snowflake;
        this.crescentMoon = crescentMoon;
        this.umbrella = umbrella;
        this.exclamation = exclamation;
        this.smallRedTriangleDown = smallRedTriangleDown;
        this.clock12 = clock12;
        this.check = check;
        this.grey_exclamation = grey_exclamation;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getThermometer() {
        return thermometer;
    }

    public String getWindBlowingFace() {
        return windBlowingFace;
    }

    public String getCloud() {
        return cloud;
    }

    public String getFog() {
        return fog;
    }

    public String getThunderCloudRain() {
        return thunderCloudRain;
    }

    public String getWhiteSunSmallCloud() {
        return whiteSunSmallCloud;
    }

    public String getWhiteSunBehindCloud() {
        return whiteSunBehindCloud;
    }

    public String getWhiteSunBehindCloudRain() {
        return whiteSunBehindCloudRain;
    }

    public String getCloudRain() {
        return cloudRain;
    }

    public String getCloudSnow() {
        return cloudSnow;
    }

    public String getCloudLightning() {
        return cloudLightning;
    }

    public String getPartlySunny() {
        return partlySunny;
    }

    public String getSunny() {
        return sunny;
    }

    public String getSnowflake() {
        return snowflake;
    }

    public String getCrescentMoon() {
        return crescentMoon;
    }

    public String getUmbrella() {
        return umbrella;
    }

    public String getExclamation() {
        return exclamation;
    }

    public String getSmallRedTriangleDown() {
        return smallRedTriangleDown;
    }

    public String getClock12() {
        return clock12;
    }

    public String getCheck() {
        return check;
    }

    public String getGrey_exclamation() {
        return grey_exclamation;
    }
    
    
}
