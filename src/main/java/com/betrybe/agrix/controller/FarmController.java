package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.CropDto;
import com.betrybe.agrix.controller.dto.CropResponseDto;
import com.betrybe.agrix.controller.dto.FarmDto;
import com.betrybe.agrix.exceptions.FarmNotFoundException;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FarmService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Farm.
 */

@RestController
@RequestMapping("/farms")
public class FarmController {

  private final FarmService farmService;
  private final CropService cropService;

  /**
   * Constructor.
   */

  @Autowired
  public FarmController(FarmService farmService, CropService cropService) {
    this.farmService = farmService;
    this.cropService = cropService;
  }

  /**
   * Post Mapping.
   */
  @PostMapping
  public ResponseEntity<Farm> insertFarm(@RequestBody FarmDto farmDto) {
    Farm farm = farmDto.toFarm();
    Farm farmCreated = this.farmService.insertFarm(farm);
    return ResponseEntity.status(HttpStatus.CREATED).body(farmCreated);
  }


  @GetMapping
  @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
  public ResponseEntity<List<Farm>> getAllFarms() {
    List<Farm> allFarms = this.farmService.getFarms();
    return ResponseEntity.status(HttpStatus.OK).body(allFarms);
  }

  /**
   * Get Farm by id.
   */

  @GetMapping("/{id}")
  public ResponseEntity getFarmById(@PathVariable Long id) throws FarmNotFoundException {
    try {
      Optional<Farm> farmById = this.farmService.getFarmById(id);

      if (farmById.isEmpty()) {
        throw new FarmNotFoundException();
      }
      Farm responseFarm = farmById.get();
      return ResponseEntity.status(HttpStatus.OK).body(responseFarm);
    } catch (FarmNotFoundException farmNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(farmNotFoundException.getMessage());
    }
  }

  /**
   *  Post de crops pelo 'id' da Farm.
   */

  @PostMapping("/{farmId}/crops")
  public ResponseEntity insertCropByFarmId(@PathVariable Long farmId,
                                           @RequestBody CropDto cropDto)
      throws FarmNotFoundException {
    try {
      Optional<Farm> farmToAdd = this.farmService.getFarmById(farmId);

      if (farmToAdd.isEmpty()) {
        throw new FarmNotFoundException();
      }

      Farm farm = farmToAdd.get();
      Crop crop = cropDto.toCrop(farm);

      Crop responseCrop = this.cropService.insertCrop(crop);

      CropResponseDto cropResponseDto = new CropResponseDto(
          responseCrop.getId(), responseCrop.getName(),
          responseCrop.getPlantedArea(), responseCrop.getPlantedDate(),
          responseCrop.getHarvestDate(), responseCrop.getFarm().getId());

      return ResponseEntity.status(HttpStatus.CREATED).body(cropResponseDto);

    } catch (FarmNotFoundException farmNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(farmNotFoundException.getMessage());
    }
  }

  /**
   * Get crops by Farm 'id'.
   */
  @GetMapping("/{farmId}/crops")
  public ResponseEntity getCropsByFarmId(@PathVariable Long farmId) {
    try {

      List<Crop> crops = this.farmService.getCorps(farmId);
      List<CropResponseDto> responseCrops = crops.stream().map(
          crop -> new CropResponseDto(crop.getId(), crop.getName(),
              crop.getPlantedArea(), crop.getPlantedDate(), crop.getHarvestDate(),
              crop.getFarm().getId())).collect(Collectors.toList());

      return ResponseEntity.status(HttpStatus.OK).body(responseCrops);

    } catch (FarmNotFoundException farmNotFoundException) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(farmNotFoundException.getMessage());

    }
  }
}

