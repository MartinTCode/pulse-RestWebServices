package com.pulse.api.dto;

import java.time.LocalDate;
/**
* Data Transfer Object for Ladok Result
*/
public record LadokResultDTO (
    String personalNo,
    String courseId,
    String moduleCode,
    String grade,
    LocalDate examDate
    
) {}
