package com.betrybe.agrix.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.betrybe.agrix.model.entities.Person;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service do token.
 */

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  /**
   * Gerador do Token.
   */

  public String generateToken(Person person) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create().withIssuer("agrix")
        .withExpiresAt(expiresAt())
        .withSubject(person.getUsername())
        .sign(algorithm);
  }

  /**
   * Tempo para expirar o token.
   */

  public Instant expiresAt() {
    LocalDateTime localDateTime = LocalDateTime.now();
    return localDateTime.plusHours(48).toInstant(ZoneOffset.of("-03:00"));
  }

  /**
   * Validador do Token.
   */

  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.require(algorithm).withIssuer("agrix")
        .build().verify(token).getSubject();
  }

}
