package ca.ulaval.glo2003.bookings.rest;

import ca.ulaval.glo2003.parsers.rest.AbstractParser;
import ca.ulaval.glo2003.parsers.rest.SerializingModule;
import java.util.List;

public class BookingParser extends AbstractParser {

  public BookingParser(List<SerializingModule> modules) {
    super(modules);
  }
}
