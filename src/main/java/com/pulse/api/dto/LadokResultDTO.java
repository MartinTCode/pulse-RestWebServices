package com.pulse.api.dto;

import java.time.LocalDate;

public record LadokResultDTO (
    String personalNo,
    String courseId,
    String moduleCode,
    String grade,
    LocalDate examDate
    
) {}
