package com.betrybe.agrix.utils;

import com.betrybe.agrix.model.entities.Person;
import com.betrybe.agrix.service.PersonService;
import com.betrybe.agrix.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Security filter.
 */

@Component
public class SecurityFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final PersonService personService;

  @Autowired
  public SecurityFilter(TokenService tokenService, PersonService personService) {
    this.tokenService = tokenService;
    this.personService = personService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {
    String token = recoveryToken(request);

    if (token != null) {
      String username = this.tokenService.validateToken(token);

      UserDetails user = this.personService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  private  String recoveryToken(HttpServletRequest request) {
    String auth = request.getHeader("Authorization");
    if (auth == null) {
      return null;
    }
    return auth.replace("Bearer ", "");
  }

}
