package com.pulse.api.dto;

public record StudentItsDTO(
    String personalNo,
    String firstName,
    String lastName,
    String email,
    String courseId
) {}
