package com.pulse.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulse.api.dto.EpokModuleDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.*;
import java.util.List;
/**
 * Client for interacting with the Epok API to retrieve module information.
 */
public class EpokApiClient {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String BASE_URL = "http://localhost:8080/api/modules";

    /**
     * Gets module Data Transfer Objects with the specified course ID.
     * @param String chosen course ID
     * @return List<EpokModuleDTO> containing the Epok Module Data Transfer Objects with the chosen course ID
     * @throws Exception if the HTTP request fails or response cannot be parsed
     */
    public static List<EpokModuleDTO> getModulesByCourseId(String courseId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/" + courseId))
            .header("Accept", "application/json")
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch modules from Epok API: " + response.statusCode());
        }

        return mapper.readValue(
            response.body(),
            new TypeReference<List<EpokModuleDTO>>() {}
        );
    }
}
