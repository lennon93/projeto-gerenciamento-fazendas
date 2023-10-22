package com.betrybe.agrix.model.repositories;

import com.betrybe.agrix.model.entities.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Person JPA repository.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByUsername(String username);
}
