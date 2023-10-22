package com.betrybe.agrix.service;

import com.betrybe.agrix.exceptions.FarmNotFoundException;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.model.repositories.FarmRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 *  Service do Farm.
 */

@Service
public class FarmService {

  private final FarmRepository farmRepository;

  @Autowired
  public FarmService(FarmRepository farmRepository) {
    this.farmRepository = farmRepository;
  }

  public List<Farm> getFarms() {
    return this.farmRepository.findAll();
  }

  public Farm insertFarm(Farm newFarm) {
    return this.farmRepository.save(newFarm);
  }

  public Optional<Farm> getFarmById(Long id) {
    return this.farmRepository.findById(id);
  }

  /**
   * Get todos os Corps.
   */

  public List<Crop> getCorps(Long id) throws FarmNotFoundException {
    Optional<Farm> farm = this.farmRepository.findById(id);

    if (farm.isEmpty()) {
      throw new FarmNotFoundException();
    }

    Farm farmById = farm.get();
    return farmById.getCrops();
  }

}
