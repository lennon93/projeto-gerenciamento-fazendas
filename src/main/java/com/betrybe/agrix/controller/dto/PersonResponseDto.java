package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.security.Role;

/**
 * Response Dto do person.
 */

public record PersonResponseDto(Long id, String username, Role role) {
}
