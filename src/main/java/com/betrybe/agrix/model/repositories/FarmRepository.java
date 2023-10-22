package com.betrybe.agrix.model.repositories;

import com.betrybe.agrix.model.entities.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio do Farm.
 */
public interface FarmRepository extends JpaRepository<Farm, Long> {
}
