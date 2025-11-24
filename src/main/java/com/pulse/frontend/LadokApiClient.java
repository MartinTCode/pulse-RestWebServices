package com.pulse.frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.*;
import java.util.List;

public class LadokApiClient {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String URL = "http://localhost:8080/api/ladok/transfer";

    public static List<LadokResponseDTO> sendResults(List<LadokResultDTO> results) throws Exception {
        String json = mapper.writeValueAsString(results);

         HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to send results to Ladok API: " + response.statusCode());
        }

        return mapper.readValue(
            response.body(),
            new TypeReference<List<LadokResponseDTO>>() {}
        );

    }
    
}
