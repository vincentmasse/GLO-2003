package ca.ulaval.glo2003.beds.rest.exceptions;

public class InvalidCleaningFrequencyException extends RuntimeException {

  public InvalidCleaningFrequencyException() {
    super("INVALID_CLEANING_FREQUENCY");
  }
}