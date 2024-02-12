package com.jport.pollencheck.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class WeatherMocker implements Weather {

    public WeatherMocker() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getPollenString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getPollen() {
        Resource resource = new ClassPathResource("mocks/mockdata_manchester.json");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
//          JsonNode rootNode = objectMapper.readTree(resource.getInputStream());
            Map<String, Object> rootNodeMap = objectMapper.readValue(resource.getInputStream(), Map.class);


//            return WeatherUtils.extractPollenData(rootNodeMap);
            return WeatherUtils.extractData(rootNodeMap, "AirAndPollen","Grass");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
