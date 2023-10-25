package com.betrybe.agrix.controller;


import com.betrybe.agrix.controller.dto.PersonDto;
import com.betrybe.agrix.controller.dto.PersonResponseDto;
import com.betrybe.agrix.model.entities.Person;
import com.betrybe.agrix.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller da rota person.
 */

@RestController
@RequestMapping("/persons")
public class PersonController {
  private final PersonService personService;

  /**
   * Construtor da classe.
   */

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Post de person.
   */

  @PostMapping
  public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonDto personDto) {
    Person personToAdd = personDto.toPerson();
    Person person = this.personService.create(personToAdd);

    PersonResponseDto responsePerson = new PersonResponseDto(person.getId(),
        person.getUsername(), person.getRole());

    return ResponseEntity.status(HttpStatus.CREATED).body(responsePerson);
  }
}
