package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.model.entities.Person;
import com.betrybe.agrix.security.Role;

/**
 * Person Dto.
 */

public record PersonDto(Long id, String username, String password, Role role) {
  public Person toPerson() {
    return new Person(id, username, password, role);
  }
}
