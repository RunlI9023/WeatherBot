package com.ilnur.BenderWeatherAssistBot.WeatherForecastForGeoPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExampleForecastGeo {

    @JsonProperty("cod")
    private String cod;
    @JsonProperty("message")
    private Integer message;
    @JsonProperty("cnt")
    private Integer cnt;
    @JsonProperty("list")
    private java.util.List<ListForecastGeo> list;
    @JsonProperty("city")
    private CityForecastGeo city;

    public ExampleForecastGeo() {}

    public ExampleForecastGeo(String cod, Integer message, Integer cnt, java.util.List<ListForecastGeo> list, CityForecastGeo city) {
        super();
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
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

    public java.util.List<ListForecastGeo> getList() {
        return list;
    }

    public void setList(java.util.List<ListForecastGeo> list) {
        this.list = list;
    }

    public CityForecastGeo getCity() {
        return city;
    }

    public void setCity(CityForecastGeo city) {
        this.city = city;
    }
    
    public List<String> getDtTxt() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getDtTxt())
                .collect(Collectors.toList());
    }
        
    public List<String> getDescription() {
        return getList()
                .stream()
                .flatMap(listForecastGeo -> listForecastGeo.getWeather().stream())
                .map(weatherForecastForGeoposition -> weatherForecastForGeoposition.getDescription())
                .collect(Collectors.toList());
    }
    
    public List<Double> getTempMax() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getMain().getTempMax())
                .collect(Collectors.toList());
    }
    
    public List<Double> getTempMin() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getMain().getTempMin())
                .collect(Collectors.toList());
    }
    
    public List<Integer> getVisibility() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getVisibility())
                .collect(Collectors.toList());
    }
        
    public List<Double> getWindSpeed() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getWind().getSpeed())
                .collect(Collectors.toList());
    }
    
    public List<Integer> getClouds() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getClouds().getAll())
                .collect(Collectors.toList());
    }
    
    public List<Double> getTempFeelsLike() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getMain().getFeelsLike())
                .collect(Collectors.toList());
    }
    
    public List<Integer> getPressure() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getMain().getPressure())
                .collect(Collectors.toList());
    }
    
    public List<Integer> getHumidity() {
        return getList()
                .stream()
                .map(listForecastGeo -> listForecastGeo.getMain().getHumidity())
                .collect(Collectors.toList());
    }
    
    public List<ResultForecastMessageForGeoposition> weatherForecastForGeopositionObjectsFilling() {
        List<ResultForecastMessageForGeoposition> forecastMessageListForGeoposition = new ArrayList<>();
        for (int i = 0; i < getDtTxt().size(); i++) {
            ResultForecastMessageForGeoposition resultForecastMessageForGeoposition = new ResultForecastMessageForGeoposition();
            resultForecastMessageForGeoposition.setDate(getDtTxt().get(i));
            resultForecastMessageForGeoposition.setDescription(getDescription().get(i));
            resultForecastMessageForGeoposition.setTempMaximum(getTempMax().get(i));
            resultForecastMessageForGeoposition.setTempMinimum(getTempMin().get(i));
            resultForecastMessageForGeoposition.setTempFeelsLike(getTempFeelsLike().get(i));
            resultForecastMessageForGeoposition.setHumidity(getHumidity().get(i));
            resultForecastMessageForGeoposition.setPressure(getPressure().get(i));
            resultForecastMessageForGeoposition.setVisibility(getVisibility().get(i));
            resultForecastMessageForGeoposition.setClouds(getClouds().get(i));
            resultForecastMessageForGeoposition.setWindSpeed(getWindSpeed().get(i));
            forecastMessageListForGeoposition.add(resultForecastMessageForGeoposition);
        }
        return forecastMessageListForGeoposition;
    } 
       
    public Map<String, List<ResultForecastMessageForGeoposition>> groupingForecastForGeopositionMessageByDate() {
        return weatherForecastForGeopositionObjectsFilling()
            .stream()
            .collect(Collectors.groupingBy(d -> d.getDate().substring(0, 10)));
    }
    
    public Map<String, List<ResultForecastMessageForGeoposition>> resultForecastMessageForGeoposition() {
            return new TreeMap<>(groupingForecastForGeopositionMessageByDate());
    }
}
