package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.model.entities.Farm;

/**
 * Camada de Dto do Farms.
 */

public record FarmDto(Long id, String name, Double size) {

  /**
   * Construtor.
   */
  public Farm toFarm() {
    return new Farm(id, name, size);
  }
}
