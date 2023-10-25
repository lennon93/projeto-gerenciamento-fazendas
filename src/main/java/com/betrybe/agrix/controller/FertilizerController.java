package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.FertilizerDto;
import com.betrybe.agrix.exceptions.FertilizerNotFoundException;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.service.FertilizerService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




/**
 *  Controller do Fertilizer.
 */

@RestController
@RequestMapping("/fertilizers")
public class FertilizerController {
  private final FertilizerService fertilizerService;

  public FertilizerController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  /**
   * Post Mapping.
   */
  @PostMapping
  public ResponseEntity<Fertilizer> insertFertilizer(@RequestBody FertilizerDto fertilizerDto) {
    Fertilizer fertilizer = fertilizerDto.toFertilizer();
    Fertilizer fertilizerCreated = this.fertilizerService.insertFertilizer(fertilizer);
    return ResponseEntity.status(HttpStatus.CREATED).body(fertilizerCreated);
  }

  @GetMapping
  @Secured("ROLE_ADMIN")
  public ResponseEntity<List<Fertilizer>> getAllFertilizers() {
    List<Fertilizer> allFertilizers = this.fertilizerService.getFertilizers();
    return ResponseEntity.status(HttpStatus.OK).body(allFertilizers);
  }

  /**
   * Get Farm by id.
   */

  @GetMapping("/{id}")
  public ResponseEntity getFertilizerById(@PathVariable Long id)
      throws FertilizerNotFoundException {
    try {
      Optional<Fertilizer> fertilizerById = this.fertilizerService.getFertilizerById(id);

      if (fertilizerById.isEmpty()) {
        throw new FertilizerNotFoundException();
      }
      Fertilizer responseFertilizer = fertilizerById.get();
      return ResponseEntity.status(HttpStatus.OK).body(responseFertilizer);
    } catch (FertilizerNotFoundException fertilizerNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(fertilizerNotFoundException.getMessage());
    }
  }

}
