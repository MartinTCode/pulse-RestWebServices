package com.pulse.api.dto;

public record EpokModuleDTO(
    int moduleId,
    String moduleCode,
    String moduleName,
    String courseId,
    Double credits
) {}