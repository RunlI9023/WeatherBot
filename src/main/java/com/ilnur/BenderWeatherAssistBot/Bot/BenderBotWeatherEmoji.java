package com.ilnur.BenderWeatherAssistBot.Bot;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

@Component
public class BenderBotWeatherEmoji {
    
    private String humid = ":droplet:";
    private String humidPars = EmojiParser.parseToUnicode(humid);//капля влажность
    private String therm = ":thermometer:";
    private String thermPars = EmojiParser.parseToUnicode(therm);//термометр
    private String wind = ":wind_blowing_face:";
    private String windPars = EmojiParser.parseToUnicode(wind);//лицо с ветром
    private String cloud = ":cloud:";
    private String cloudPars = EmojiParser.parseToUnicode(cloud);//облако
    private String fog = ":fog:";
    private String fogPars = EmojiParser.parseToUnicode(fog);//tuman
    private String thunderCloudRain = ":thunder_cloud_rain:";
    private String thunderCloudRainPars = EmojiParser.parseToUnicode(thunderCloudRain);
    private String whiteSunSmallCloud = ":white_sun_small_cloud:";//
    private String whiteSunSmallCloudPars = EmojiParser.parseToUnicode(whiteSunSmallCloud);
    private String whiteSunBehindCloud = ":white_sun_behind_cloud:";//
    private String whiteSunBehindCloudPars = EmojiParser.parseToUnicode(whiteSunBehindCloud);
    private String whiteSunBehindCloudRain = ":white_sun_behind_cloud_rain:";////
    private String whiteSunBehindCloudRainPars = EmojiParser.parseToUnicode(whiteSunBehindCloudRain);
    private String cloudRain = ":cloud_rain:";////
    private String cloudRainPars = EmojiParser.parseToUnicode(cloudRain);
    private String cloudSnow = ":cloud_snow:";////
    private String cloudSnowPars = EmojiParser.parseToUnicode(cloudSnow);
    private String cloudLightning = ":cloud_lightning:";////туча с молнией
    private String cloudLightningPars = EmojiParser.parseToUnicode(cloudLightning);
    private String partlySunny = ":partly_sunny:";////солнце и облако
    private String partlySunnyPars = EmojiParser.parseToUnicode(partlySunny);
    private String sunny = ":sunny:";////солнце
    private String sunnyPars = EmojiParser.parseToUnicode(sunny);
    private String snowflake = ":snowflake:";////снежинка
    private String snowflakePars = EmojiParser.parseToUnicode(snowflake);
    private String crescentMoon = ":crescent_moon:";////keyf
    private String crescentMoonPars = EmojiParser.parseToUnicode(crescentMoon);

    public String getHumidPars() {
        return humidPars;
    }

    public String getThermPars() {
        return thermPars;
    }

    public String getWindPars() {
        return windPars;
    }

    public String getCloudPars() {
        return cloudPars;
    }

    public String getFogPars() {
        return fogPars;
    }

    public String getThunderCloudRainPars() {
        return thunderCloudRainPars;
    }

    public String getWhiteSunSmallCloudPars() {
        return whiteSunSmallCloudPars;
    }

    public String getWhiteSunBehindCloudPars() {
        return whiteSunBehindCloudPars;
    }

    public String getWhiteSunBehindCloudRainPars() {
        return whiteSunBehindCloudRainPars;
    }

    public String getCloudRainPars() {
        return cloudRainPars;
    }

    public String getCloudSnowPars() {
        return cloudSnowPars;
    }

    public String getCloudLightningPars() {
        return cloudLightningPars;
    }

    public String getPartlySunnyPars() {
        return partlySunnyPars;
    }

    public String getSunnyPars() {
        return sunnyPars;
    }

    public String getSnowflakePars() {
        return snowflakePars;
    }

    public String getCrescentMoonPars() {
        return crescentMoonPars;
    }
    

            
    
}
