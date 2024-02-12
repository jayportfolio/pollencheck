package com.jport.pollencheck.common;

import java.util.*;

public class WeatherUtils {
    public static Map<String, Object> extractPollenData(Map<String, Object> rootNodeMap) {
        List<LinkedHashMap<String, Object>> airQualityMap = (List<LinkedHashMap<String, Object>>) ((HashMap) ((ArrayList) rootNodeMap.get("DailyForecasts")).get(0)).get("AirAndPollen");

//                for (Map<String, Object> map : airQualityMap) {
//                    Object name = map.get("Name");
//                    if (name != null && name.equals("Grass")) {
//                        airData = map;
//                        break;
//                    }
//                }
        Optional<LinkedHashMap<String, Object>> airDataMapOptional = airQualityMap.stream()
                .filter(map -> "Grass".equals(map.get("Name")))
                .findFirst();
        Map<String, Object> airData = airDataMapOptional.get();

        return airData;
    }
    public static Map<String, Object> extractData(Map<String, Object> rootNodeMap, String groupKey, String itemKey) {
        Map<String, Object> rootNodeMap1 = (Map<String, Object>) extractForecasts(rootNodeMap).get(0);
        List<LinkedHashMap<String, Object>> airQualityMap = extractDataList(rootNodeMap1, groupKey);
        Map<String, Object> stringObjectMap = extractDataItemFromArray(airQualityMap, itemKey);
        return stringObjectMap;
    }

    public static ArrayList extractForecasts(Map<String, Object> rootNodeMap) {
        return (ArrayList) rootNodeMap.get("DailyForecasts");
    }
    public static List<LinkedHashMap<String, Object>> extractDataList(Map<String, Object> dailyForecastDay1, String groupKey) {

//        ArrayList dailyForecastList = (ArrayList) rootNodeMap.get("DailyForecasts");
//        HashMap dailyForecastDay1 = (HashMap) dailyForecastList.get(0);
        List<LinkedHashMap<String, Object>> dataGroupMap = (List<LinkedHashMap<String, Object>>) dailyForecastDay1.get(groupKey);

        return dataGroupMap;
    }

    public static LinkedHashMap<String, Object> extractDataItem(Map<String, Object> dailyForecastDay1, String groupKey) {

//        ArrayList dailyForecastList = (ArrayList) rootNodeMap.get("DailyForecasts");
//        HashMap dailyForecastDay1 = (HashMap) dailyForecastList.get(0);
        LinkedHashMap<String, Object> dataGroupMap = (LinkedHashMap<String, Object>) dailyForecastDay1.get(groupKey);

        return dataGroupMap;
    }

    public static Map<String, Object> extractDataItemFromArray(List<LinkedHashMap<String, Object>> airQualityMap, String itemKey) {
        Optional<LinkedHashMap<String, Object>> airDataMapOptional = airQualityMap.stream()
                .filter(map -> itemKey.equals(map.get("Name")))
                .findFirst();
        Map<String, Object> airData = airDataMapOptional.get();

        return airData;
    }

}
