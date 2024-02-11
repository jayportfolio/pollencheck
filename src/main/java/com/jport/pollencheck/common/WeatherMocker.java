package com.jport.pollencheck.common;

import com.fasterxml.jackson.databind.JsonNode;
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
        Map<String, Object> jsonData = null;
        Map<String, Object> jsonData1 = null;
        try {
            jsonData1 = objectMapper.readValue(resource.getInputStream(), Map.class);

            JsonNode rootNode = objectMapper.readTree(resource.getInputStream());

            JsonNode pollenViaNode = rootNode.get("DailyForecasts").get(0).get("AirAndPollen").get(0);
            jsonData = jsonNodeToMap(pollenViaNode);

            List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) ((HashMap) ((ArrayList) jsonData1.get("DailyForecasts")).get(0)).get("AirAndPollen");

            if (false) {
                for (Map<String, Object> map : list) {
                    Object name = map.get("Name");
                    if (name != null && name.equals("Grass")) {
                        jsonData = map;
                        break;
                    }
                }
           } else {
                Optional<LinkedHashMap<String, Object>> optionalMap = list.stream()
                        .filter(map -> "Grass".equals(map.get("Name")))
                        .findFirst();
                jsonData = optionalMap.get();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jsonData;
    }

    private Map<String, Object> jsonNodeToMap(JsonNode jsonNode) {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> fieldNames = jsonNode.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = jsonNode.get(fieldName);

            if (fieldValue.isObject()) {
                map.put(fieldName, jsonNodeToMap(fieldValue));
            } else if (fieldValue.isArray()) {
                // Handle array case if needed
            } else {
                map.put(fieldName, fieldValue.asText());
            }
        }

        return map;
    }
}
