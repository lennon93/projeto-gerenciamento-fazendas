package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.LoginDto;
import com.betrybe.agrix.controller.dto.TokenResponse;
import com.betrybe.agrix.model.entities.Person;
import com.betrybe.agrix.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller da autenticação.
 */

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  /**
   * Construtor do controller de autenticação.
   */

  @Autowired
  public AuthenticationController(
      AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * Login.
   */

  @PostMapping("/login")
  public ResponseEntity loginPerson(@RequestBody LoginDto loginDto) {
    try {
      UsernamePasswordAuthenticationToken usernamePassword =
          new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());
      Authentication auth = this.authenticationManager.authenticate(usernamePassword);

      Person person = (Person) auth.getPrincipal();

      String token = this.tokenService.generateToken(person);

      TokenResponse tokenResponse = new TokenResponse(token);

      return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }



}
