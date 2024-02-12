package com.jport.pollencheck.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Pollen implements Weather {

    @Value("${my.env.variable}") // Reads the environment variable from properties file or environment variables
    private String apiKey;


    public Pollen() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    // define our init method
    @PostConstruct
    public void doMyStartupStuff() {
        System.out.println("In doMyStartupStuff(): " + getClass().getSimpleName());
    }

    // define our destroy method
    @PreDestroy
    public void doMyCleanupStuff() {
        System.out.println("In doMyCleanupStuff(): " + getClass().getSimpleName());
    }


    @Override
    public String getPollenString() {
        return "TBC";
    }

    @Override
    public Map<String, Object> getPollen() {

        Map rootMap;
        try {
            final String uri = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/329260?apikey=" + apiKey + "&details=true&metric=true";

            RestTemplate restTemplate = new RestTemplate();
            rootMap = restTemplate.getForObject(uri, Map.class);

        } catch (Exception e) {
            final String uri = "http://localhost:7070/todayCached";

            RestTemplate restTemplate = new RestTemplate();
            rootMap = restTemplate.getForObject(uri, Map.class);
        }

        Map<String, Object> forecastDay1 = (Map<String, Object>) WeatherUtils.extractForecasts(rootMap).get(0);

//        Map grassPollenMap = WeatherUtils.extractPollenData(rootMap);
        List pollenMap = WeatherUtils.extractDataList(forecastDay1, "AirAndPollen");
        Map grassPollenMap = WeatherUtils.extractData(rootMap, "AirAndPollen","Grass");
        LinkedHashMap<String, Object> sunUpMap = WeatherUtils.extractDataItem(forecastDay1, "Sun");
        System.out.println(grassPollenMap);
//        return grassPollenMap;

        Map<String, Object> combinedMapFull = new HashMap<>();
        combinedMapFull.put("Grass Pollen",grassPollenMap);
        combinedMapFull.put("Sunrise/Sunset",sunUpMap);
        combinedMapFull.put("Sunrise/Sunset",sunUpMap);

        Map<String, Object> combinedMap = new LinkedHashMap<>();
        combinedMap.put("Grass Pollen",grassPollenMap.get("Category"));
        combinedMap.put("Grass Pollen Exact Value",grassPollenMap.get("Value"));
        combinedMap.put("Sunrise",sunUpMap.get("Rise"));
        combinedMap.put("Sunset",sunUpMap.get("Set"));
        combinedMap.put("Air Quality",pollenMap);
        return combinedMap;
    }
}
