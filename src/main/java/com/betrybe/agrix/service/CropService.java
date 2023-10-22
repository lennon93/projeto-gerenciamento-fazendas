package com.betrybe.agrix.service;

import com.betrybe.agrix.exceptions.CropNotFoundException;
import com.betrybe.agrix.exceptions.FertilizerNotFoundException;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.model.repositories.CropRepository;
import com.betrybe.agrix.model.repositories.FertilizerRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




/**
 * CropService.
 */

@Service
public class CropService {

  private final CropRepository cropRepository;
  private final FertilizerRepository fertilizerRepository;

  @Autowired
  public CropService(CropRepository cropRepository, FertilizerRepository fertilizerRepository) {
    this.cropRepository = cropRepository;
    this.fertilizerRepository = fertilizerRepository;
  }

  /**
   * Get all Crops.
   */

  public List<Crop> getAllCrops() {
    return this.cropRepository.findAll();
  }


  /**
   * Insert crop.
   */

  public Crop insertCrop(Crop newCrop) {
    Crop cropToInsert = this.cropRepository.save(newCrop);

    Optional<Crop> cropById = this.cropRepository.findById(cropToInsert.getId());

    return cropById.get();
  }

  /**
   * Get crop by 'id'.
   */

  public Crop getCropById(Long id) throws CropNotFoundException {
    Optional<Crop> crop = this.cropRepository.findById(id);

    if (crop.isEmpty()) {
      throw new CropNotFoundException();
    }

    return crop.get();
  }

  /**
   * Get Crops by Date.
   */

  public List<Crop> getByDate(LocalDate startDate, LocalDate endDate) {
    List<Crop> crops = this.cropRepository.findAll();

    return crops.stream().filter(crop -> startDate.isBefore(crop.getHarvestDate())
    && endDate.isAfter(crop.getHarvestDate())).toList();
  }

  /**
   * Insere do fertilizer pelo "id" do crop.
   */

  public Crop setFertilizerByCropId(Long cropId, Long fertilizerId)
      throws FertilizerNotFoundException, CropNotFoundException {
    Optional<Crop> crop = this.cropRepository.findById(cropId);
    Optional<Fertilizer> fertilizer = this.fertilizerRepository.findById(fertilizerId);

    if (fertilizer.isEmpty()) {
      throw new FertilizerNotFoundException();
    }

    if (crop.isEmpty()) {
      throw new CropNotFoundException();
    }

    Crop cropToAdd = crop.get();
    Fertilizer fertilizerToAdd = fertilizer.get();

    cropToAdd.getFertilizers().add(fertilizerToAdd);
    return this.cropRepository.save(cropToAdd);
  }


}
