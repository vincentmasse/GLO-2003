package ca.ulaval.glo2003.locations.rest.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo2003.errors.rest.factories.CatchallErrorStatusFactory;
import ca.ulaval.glo2003.locations.exceptions.InvalidZipCodeException;
import ca.ulaval.glo2003.locations.exceptions.LocationException;
import ca.ulaval.glo2003.locations.exceptions.NonExistingZipCodeException;
import ca.ulaval.glo2003.locations.exceptions.UnreachableZippopotamusServerException;
import java.util.stream.Stream;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LocationErrorStatusFactoryTest {

  private static LocationErrorStatusFactory locationErrorStatusFactory;

  @BeforeAll
  public static void setUpFactory() {
    locationErrorStatusFactory = new LocationErrorStatusFactory();
  }

  @ParameterizedTest
  @MethodSource("provideStatusForExternalServiceException")
  public void create_withExternalServiceException_shouldCreateAssociatedStatus(
      LocationException exception, int expectedStatus) {
    int status = locationErrorStatusFactory.create(exception);

    assertEquals(expectedStatus, status);
  }

  @Test
  public void create_withException_shouldCreateDefaultStatus() {
    Exception exception = new Exception();
    int expectedStatus = new CatchallErrorStatusFactory().create(exception);

    int status = locationErrorStatusFactory.create(exception);

    assertEquals(expectedStatus, status);
  }

  private static Stream<Arguments> provideStatusForExternalServiceException() {
    return Stream.of(
        Arguments.of(new InvalidZipCodeException(), HttpStatus.BAD_REQUEST_400),
        Arguments.of(new NonExistingZipCodeException(), HttpStatus.BAD_REQUEST_400),
        Arguments.of(
            new UnreachableZippopotamusServerException(), HttpStatus.SERVICE_UNAVAILABLE_503));
  }
}
