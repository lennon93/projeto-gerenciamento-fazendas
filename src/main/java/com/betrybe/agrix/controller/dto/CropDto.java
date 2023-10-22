package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import java.time.LocalDate;


/**
 * CropDto.
 */
public record CropDto(
    String name, Double plantedArea, LocalDate plantedDate, LocalDate harvestDate
) {

  /**
   * toCrop para criar o DTo de Crop.
   */

  public Crop toCrop(Farm farm) {
    return new Crop(name, plantedArea, plantedDate, harvestDate, farm);
  }
}
