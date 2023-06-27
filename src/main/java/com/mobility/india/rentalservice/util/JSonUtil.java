package com.mobility.india.rentalservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSonUtil {
    /**
     * Converts given Object ot JSON
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Serialize the object to JSON string
            String jsonString = objectMapper.writeValueAsString(o);

            // Print the JSON string
            System.out.println(jsonString);

            return  jsonString;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
