package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.CropResponseDto;
import com.betrybe.agrix.exceptions.CropNotFoundException;
import com.betrybe.agrix.exceptions.FertilizerNotFoundException;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.service.CropService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * ControllerCrop.
 */

@RestController
@RequestMapping("/crops")
public class CropController {
  private final CropService cropService;

  /**
   * Construtor.
   */

  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }

  /**
   * getMapping.
   */
  @GetMapping
  @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
  public ResponseEntity<List<CropResponseDto>> getAllCrops() {

    List<Crop> crops = this.cropService.getAllCrops();

    List<CropResponseDto> cropsResponse = crops.stream()
        .map(crop -> new CropResponseDto(crop.getId(), crop.getName(),
            crop.getPlantedArea(), crop.getPlantedDate(), crop.getHarvestDate(),
            crop.getFarm().getId())).toList();

    return ResponseEntity.status(HttpStatus.OK).body(cropsResponse);
  }

  /**
   * Get de CropByDate.
   */

  @GetMapping("/search")
  public ResponseEntity<List<CropResponseDto>> getByDate(@RequestParam LocalDate start,
                                                         @RequestParam LocalDate end) {
    List<Crop> crops = this.cropService.getByDate(start, end);

    List<CropResponseDto> cropsResponse = crops.stream()
        .map(crop -> new CropResponseDto(crop.getId(), crop.getName(),
            crop.getPlantedArea(), crop.getPlantedDate(), crop.getHarvestDate(),
            crop.getFarm().getId())).toList();

    return ResponseEntity.status(HttpStatus.OK).body(cropsResponse);
  }


  /**
   * Get de CropById.
   */

  @GetMapping("/{id}")
  public ResponseEntity getCropById(@PathVariable Long id) {
    try {
      Crop cropById = this.cropService.getCropById(id);

      CropResponseDto cropResponseDto = new CropResponseDto(
          cropById.getId(), cropById.getName(),
          cropById.getPlantedArea(), cropById.getPlantedDate(),
          cropById.getHarvestDate(), cropById.getFarm().getId());

      return ResponseEntity.status(HttpStatus.OK).body(cropResponseDto);
    } catch (CropNotFoundException cropNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cropNotFoundException.getMessage());
    }
  }

  /**
   * Post de fertilizer pelo "id" do crop.
   */

  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity insertFertilizerByCropId(@PathVariable Long cropId,
                                                 @PathVariable Long fertilizerId) {
    try {
      Crop crop = this.cropService.setFertilizerByCropId(cropId, fertilizerId);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body("Fertilizante e plantação associados com sucesso!");
    } catch (FertilizerNotFoundException | CropNotFoundException notFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(notFoundException.getMessage());
    }
  }


  /**
   * Get fertilizers by Crop 'id'.
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity getFertilizersByCropId(@PathVariable Long cropId) {
    try {

      Crop cropById = this.cropService.getCropById(cropId);
      List<Fertilizer> fertilizers = cropById.getFertilizers();

      return ResponseEntity.status(HttpStatus.OK).body(fertilizers);

    } catch (CropNotFoundException cropNotFoundException) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cropNotFoundException.getMessage());

    }
  }

}
