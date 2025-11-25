package com.pulse.api.dto;

public record EpokModuleDTO(
    String moduleCode,
    String moduleName,
    String courseId,
    Double credits
) {}