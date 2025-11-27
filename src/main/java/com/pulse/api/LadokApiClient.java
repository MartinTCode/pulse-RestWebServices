package com.pulse.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pulse.api.dto.LadokResponseDTO;
import com.pulse.api.dto.LadokResultDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.*;
import java.util.List;

/**
 * Client for interacting with the Ladok API to transfer students results.
 */
public class LadokApiClient {

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(java.time.Duration.ofSeconds(30))
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()); // Handle LocalDate

    private static final String URL = "http://localhost:8080/api/ladok/transfer";

    /**
     * Sends a batch of student results to the Ladok API to be saved in Ladok database.
     * 
     * @param results List of results Data Transfer Objects to transfer
     * @return List of responses indicating success/failure for each student
     * @throws Exception if the HTTP request fails or response cannot be parsed
     */
    public static List<LadokResponseDTO> sendResults(List<LadokResultDTO> results) throws Exception {
        // Serialize results to JSON
        String json = mapper.writeValueAsString(results);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check for successful response
        if (response.statusCode() != 200) {
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
