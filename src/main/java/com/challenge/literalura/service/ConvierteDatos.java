package com.challenge.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        // Add null and empty checks
        if (json == null) {
            throw new RuntimeException("JSON content is null - API may not be returning data");
        }

        if (json.trim().isEmpty()) {
            throw new RuntimeException("JSON content is empty - API returned empty response");
        }

        // Debug output
//        System.out.println("Converting JSON to " + clase.getSimpleName());
//        System.out.println("JSON length: " + json.length());
//        System.out.println("JSON preview: " + (json.length() > 200 ? json.substring(0, 200) + "..." : json));

        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            System.err.println("JSON parsing error: " + e.getMessage());
            System.err.println("Failed JSON content: " + json);
            throw new RuntimeException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }
}