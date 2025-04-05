package com.ilnur.BenderWeatherAssistBot.WeatherForecastForCityName;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class WeatherForecastForCityNameMain {

    @JsonProperty("cod")
    private String cod;
    @JsonProperty("message")
    private Integer message;
    @JsonProperty("cnt")
    private Integer cnt;
    @JsonProperty("list")
    private java.util.List<ListForecastForCityName> list;
    @JsonProperty("city")
    private CityForecastForCityName city;

    public WeatherForecastForCityNameMain() {
    }

    public WeatherForecastForCityNameMain(String cod, Integer message, Integer cnt, java.util.List<ListForecastForCityName> list, CityForecastForCityName city) {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<ListForecastForCityName> getList() {
        return list;
    }

    public void setList(java.util.List<ListForecastForCityName> list) {
        this.list = list;
    }

    public CityForecastForCityName getCity() {
        return city;
    }

    public void setCity(CityForecastForCityName city) {
        this.city = city;
    }
    
    public List<String> getDtTxt() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getDtTxt())
                .collect(Collectors.toList());
    }
        
    public List<String> getDescription() {
        return getList()
                .stream()
                .flatMap(listForecastForCityName -> listForecastForCityName.getWeather().stream())
                .map(weatherForecastForCityName -> weatherForecastForCityName.getDescription())
                .collect(Collectors.toList());
    }
    
    public List<Double> getTempMax() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getMain().getTempMax())
                .collect(Collectors.toList());
    }
    
    public List<Double> getTempMin() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getMain().getTempMin())
                .collect(Collectors.toList());
    }
    
    public List<Integer> getVisibility() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getVisibility())
                .collect(Collectors.toList());
    }
        
    public List<Double> getWindSpeed() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getWind().getSpeed())
                .collect(Collectors.toList());
    }
    
    public List<Integer> getClouds() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getClouds().getAll())
                .collect(Collectors.toList());
    }
    
    public List<Double> getTempFeelsLike() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getMain().getFeelsLike())
                .collect(Collectors.toList());
    }
    
    public List<Integer> getPressure() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getMain().getPressure())
                .collect(Collectors.toList());
    }
    
    public List<Integer> getHumidity() {
        return getList()
                .stream()
                .map(listForecastForCityName -> listForecastForCityName.getMain().getHumidity())
                .collect(Collectors.toList());
    }
    
    public List<ForecastObjectForGrouping> weatherForecastObjectsFilling() {
        List<ForecastObjectForGrouping> forecastMessageList = new ArrayList<>();
        for (int i = 0; i < getDtTxt().size(); i++) {
            ForecastObjectForGrouping resultForecastMessage = new ForecastObjectForGrouping();
            resultForecastMessage.setDate(getDtTxt().get(i));
            resultForecastMessage.setDescription(getDescription().get(i));
            resultForecastMessage.setTempMaximum(getTempMax().get(i));
            resultForecastMessage.setTempMinimum(getTempMin().get(i));
            resultForecastMessage.setTempFeelsLike(getTempFeelsLike().get(i));
            resultForecastMessage.setHumidity(getHumidity().get(i));
            resultForecastMessage.setPressure(getPressure().get(i));
            resultForecastMessage.setVisibility(getVisibility().get(i));
            resultForecastMessage.setClouds(getClouds().get(i));
            resultForecastMessage.setWindSpeed(getWindSpeed().get(i));
            forecastMessageList.add(resultForecastMessage);
        }
        return forecastMessageList;
    } 
       
    public Map<String, List<ForecastObjectForGrouping>> groupingForecastMessageByDate() {
        return weatherForecastObjectsFilling()
            .stream()
            .collect(Collectors.groupingBy(d -> d.getDate().substring(0, 10)));
    }
    
    public Map<String, List<ForecastObjectForGrouping>> resultForecastMessage() {
            return new TreeMap<>(groupingForecastMessageByDate());
    }
}
