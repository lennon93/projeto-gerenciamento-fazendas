package com.betrybe.agrix.exceptions;

/**
 * Exception de fazenda não encontrada.
 */

public class FarmNotFoundException extends Exception {

  public FarmNotFoundException() {
    super("Fazenda não encontrada!");
  }
}
