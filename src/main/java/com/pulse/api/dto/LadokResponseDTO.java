package com.pulse.api.dto;
/**
* Data Transfer Object for Ladok Response
*/
public record LadokResponseDTO (
    String personalNo,
    String status,
    String info
){}
