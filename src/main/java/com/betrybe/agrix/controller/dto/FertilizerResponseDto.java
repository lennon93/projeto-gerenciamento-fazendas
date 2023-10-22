package com.betrybe.agrix.controller.dto;

/**
 * FertilizerResponseDto.
 */
public record FertilizerResponseDto(
    Long id, String name, String brand, String composition
) {
}
