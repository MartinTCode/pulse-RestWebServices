package com.pulse.api.dto;
/**
* Data Transfer Object for Epok Module
*/
public record EpokModuleDTO(
    int moduleId,
    String moduleCode,
    String moduleName,
    String courseId,
    Double credits
) {}