package com.betrybe.agrix.model.repositories;

import com.betrybe.agrix.model.entities.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


/**
 * Person JPA repository.
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  UserDetails findByUsername(String username);
}
