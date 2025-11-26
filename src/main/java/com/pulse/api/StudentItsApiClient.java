package com.pulse.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulse.api.dto.StudentItsDTO;

import java.net.URI;
import java.net.http.*;

public class StudentItsApiClient {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String BASE_URL = "http://localhost:8080/api/student-its";

    /**
     * Fetches a student by their studentId
     * @param studentId the student ID
     * @return StudentItsDTO with studentId and personalNo, or null if not found
     */
    public static StudentItsDTO getStudentByStudentId(String studentId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/students/" + studentId))
            .header("Accept", "application/json")
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return null; // Student not found
        }
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch student from StudentIts API: " + response.statusCode());
        }

        return mapper.readValue(response.body(), StudentItsDTO.class);
    }
}
