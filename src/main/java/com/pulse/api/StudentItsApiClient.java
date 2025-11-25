package com.pulse.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulse.api.dto.StudentItsDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.*;
import java.util.List;

public class StudentItsApiClient {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String BASE_URL = "http://localhost:8080/api/students";

    public static List<StudentItsDTO> getStudentsByCourseId(String courseId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/" + courseId))
            .header("Accept", "application/json")
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch students from StudentIts API: " + response.statusCode());
        }

        return mapper.readValue(
            response.body(),
            new TypeReference<List<StudentItsDTO>>() {}
        );
    }
}
