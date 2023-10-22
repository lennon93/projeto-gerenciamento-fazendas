package com.betrybe.agrix.exceptions;

/**
 * Exception de Plantação não encontrada.
 */

public class CropNotFoundException extends Exception {
  public CropNotFoundException() {
    super("Plantação não encontrada!");
  }
}
