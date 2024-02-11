package com.jport.pollencheck.common;

import java.util.Map;

public interface Weather {

    String getPollenString();
    Map<String, Object> getPollen();
//    JSON getPollen();
}
