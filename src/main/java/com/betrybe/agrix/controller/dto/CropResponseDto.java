package com.betrybe.agrix.controller.dto;

import java.time.LocalDate;

/**
 * CropResponseDto.
 */
public record CropResponseDto(
    Long id, String name, Double plantedArea,
    LocalDate plantedDate, LocalDate harvestDate, Long farmId
) {
}
