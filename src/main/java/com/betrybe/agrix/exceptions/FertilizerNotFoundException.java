package com.betrybe.agrix.exceptions;

/**
 * Exception de fertilizante não encontrado.
 */

public class FertilizerNotFoundException extends Exception {

  public FertilizerNotFoundException() {
    super("Fertilizante não encontrado!");
  }
}
