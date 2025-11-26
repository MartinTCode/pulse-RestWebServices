package com.pulse.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pulse.api.dto.LadokResponseDTO;
import com.pulse.api.dto.LadokResultDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.*;
import java.util.List;

public class LadokApiClient {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()); // ✅ Handle LocalDate

    private static final String URL = "http://localhost:8080/api/ladok/transfer";

    /**
     * Sends a batch of student results to the Ladok REST API.
     * 
     * @param results List of results to transfer
     * @return List of responses indicating success/failure for each student
     * @throws Exception if the HTTP request fails or response cannot be parsed
     */
    public static List<LadokResponseDTO> sendResults(List<LadokResultDTO> results) throws Exception {
        // Serialize results to JSON
        String json = mapper.writeValueAsString(results);

        // Build HTTP POST request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        // Send request and get response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check for successful response
        if (response.statusCode() != 200) {
            // Include response body in error message for debugging
            throw new RuntimeException(
                String.format("Failed to send results to Ladok API. Status: %d, Body: %s", 
                    response.statusCode(), 
                    response.body())
            );
        }

        // Parse response body to list of DTOs
        return mapper.readValue(
            response.body(),
            new TypeReference<List<LadokResponseDTO>>() {}
        );
    }
}
