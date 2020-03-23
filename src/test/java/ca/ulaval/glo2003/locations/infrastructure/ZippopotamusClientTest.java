package ca.ulaval.glo2003.locations.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.locations.domain.Location;
import ca.ulaval.glo2003.locations.exceptions.InvalidZipCodeException;
import ca.ulaval.glo2003.locations.exceptions.NonExistingZipCodeException;
import ca.ulaval.glo2003.locations.exceptions.UnreachableZippopotamusServerException;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZippopotamusClientTest {

  private static ZippopotamusClient zippopotamusClient;
  private static HttpURLConnection fakeUrlConnection;

  private static final Location LOCATION = new Location("12345", "42.8333", "-74.058");

  @BeforeAll
  public static void setUpMapper() {
    fakeUrlConnection = mock(HttpURLConnection.class);
    zippopotamusClient = new FakeZippopotamusClient(fakeUrlConnection);
  }

  @BeforeEach
  public void setUpMocks() throws IOException {
    when(fakeUrlConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
  }

  @Test
  public void validateZipCode_withValidZipCode_shouldReturnZipCode() throws IOException {
    Location actualLocation = zippopotamusClient.validateZipCode(LOCATION.getZipCode());

    assertEquals(LOCATION, actualLocation);
  }

  @Test
  public void validateZipCode_withNonExistingZipCode_shouldThrowNonExistingZipCodeException()
      throws IOException {
    when(fakeUrlConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);

    assertThrows(
        NonExistingZipCodeException.class,
        () -> zippopotamusClient.validateZipCode(LOCATION.getZipCode()));
  }

  @Test
  public void
      validateZipCode_withUnreachableZippopotamusServer_shouldThrowUnreachableZippopotamusServerException()
          throws IOException {
    when(fakeUrlConnection.getResponseCode())
        .thenThrow(new IOException())
        .thenReturn(HttpURLConnection.HTTP_OK);

    assertThrows(
        UnreachableZippopotamusServerException.class,
        () -> zippopotamusClient.validateZipCode(LOCATION.getZipCode()));
  }

  @Test
  void validateZipCode_withInvalidZipCodeLength_shouldThrowNonExistingZipCodeException() {
    String invalidZipCode = "0000";

    assertThrows(
        InvalidZipCodeException.class, () -> zippopotamusClient.validateZipCode(invalidZipCode));
  }

  @Test
  void validateZipCode_withNotAllNumericZipCode_shouldThrowNonExistingZipCodeException() {
    String invalidZipCode = "00A2E";

    assertThrows(
        InvalidZipCodeException.class, () -> zippopotamusClient.validateZipCode(invalidZipCode));
  }
}
