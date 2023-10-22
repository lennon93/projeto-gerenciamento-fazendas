package com.betrybe.agrix.model.repositories;

import com.betrybe.agrix.model.entities.Crop;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Crop Repository.
 */

public interface CropRepository extends JpaRepository<Crop, Long> {

}