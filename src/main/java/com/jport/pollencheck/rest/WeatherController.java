package com.jport.pollencheck.rest;

import com.jport.pollencheck.common.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WeatherController {

    // define a private field for the dependency
    private Weather myWeather;

    @Autowired
//    public WeatherController(@Qualifier("pollen") Weather theWeather) {
    public WeatherController(@Qualifier("weatherMocker") Weather theWeather) {
        System.out.println("In constructor: " + getClass().getSimpleName());
        myWeather = theWeather;
    }

    @GetMapping("/todayX")
    public String getTodaysPollenString() {
//        return myWeather.getPollen();
        throw new UnsupportedOperationException();
    }

    @GetMapping("/today")
    public Map<String, Object> getTodaysPollen() {
        return myWeather.getPollen();
    }

    @GetMapping
    public Map<String, String> sayHello() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("foo", "bar");
        map.put("aa", "bb");
        return map;
    }
}






