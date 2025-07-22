package com.challenge.literalura.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class ConsumoAPI {

    public String obtenerDatos(String url) {
        // Create HttpClient with redirect policy
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(30))
                .build();

        // Build the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Language", "en-US,en;q=0.9")
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

//            System.out.println("Response status code: " + response.statusCode());
//            System.out.println("Response headers: " + response.headers().map());

        } catch (IOException | InterruptedException e) {
            System.err.println("Request failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get data from API: " + e.getMessage());
        }

        // Check status code
        if (response.statusCode() >= 300 && response.statusCode() < 400) {
            System.err.println("Redirect detected but not followed properly. Status: " + response.statusCode());
            // Try to get redirect location
            var location = response.headers().firstValue("Location");
            if (location.isPresent()) {
                System.err.println("Redirect location: " + location.get());
                // Recursively follow redirect
                return obtenerDatos(location.get());
            }
        }

        if (response.statusCode() != 200) {
            System.err.println("HTTP Error. Status: " + response.statusCode());
            throw new RuntimeException("API returned an error! Status Code: " + response.statusCode());
        }

        String json = response.body();

        // Debug output
//        System.out.println("Response received: " + (json != null));
//        System.out.println("Response length: " + (json != null ? json.length() : "null"));
//
//        if (json != null && json.length() > 200) {
//            System.out.println("API response preview: " + json.substring(0, 200) + "...");
//        } else {
//            System.out.println("API response body: " + json);
//        }

        // Validate response
        if (json == null || json.trim().isEmpty()) {
            throw new RuntimeException("API returned empty response");
        }

        return json;
    }
}