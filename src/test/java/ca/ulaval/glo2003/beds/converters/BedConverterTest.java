package ca.ulaval.glo2003.beds.converters;

import static ca.ulaval.glo2003.beds.domain.helpers.BedBuilder.aBed;
import static ca.ulaval.glo2003.beds.domain.helpers.BedObjectMother.*;
import static ca.ulaval.glo2003.beds.rest.helpers.BedRequestBuilder.aBedRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.beds.domain.*;
import ca.ulaval.glo2003.beds.exceptions.InvalidBedTypeException;
import ca.ulaval.glo2003.beds.exceptions.InvalidCapacityException;
import ca.ulaval.glo2003.beds.exceptions.InvalidCleaningFrequencyException;
import ca.ulaval.glo2003.beds.exceptions.InvalidLodgingModeException;
import ca.ulaval.glo2003.beds.rest.BedRequest;
import ca.ulaval.glo2003.beds.rest.BedResponse;
import ca.ulaval.glo2003.beds.rest.PackageRequest;
import ca.ulaval.glo2003.beds.rest.PackageResponse;
import ca.ulaval.glo2003.locations.domain.Location;
import ca.ulaval.glo2003.transactions.domain.Price;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedConverterTest {

  private static BedConverter bedConverter;
  private static BedNumberConverter bedNumberConverter = mock(BedNumberConverter.class);
  private static PublicKeyConverter publicKeyConverter = mock(PublicKeyConverter.class);
  private static BloodTypesConverter bloodTypesConverter = mock(BloodTypesConverter.class);
  private static PackageConverter packageConverter = mock(PackageConverter.class);

  private static Bed bed;
  private static UUID bedNumber;
  private static PublicKey ownerPublicKey;
  private static Location location;
  private static BedTypes bedType;
  private static CleaningFrequencies cleaningFrequency;
  private static BloodTypes bloodType = mock(BloodTypes.class);
  private static List<BloodTypes> bloodTypes = Collections.singletonList(bloodType);
  private static int capacity;
  private static LodgingModes lodgingMode;
  private static Map<Packages, Price> pricesPerNight = new EnumMap<>(Packages.class);
  private static int stars;

  private static BedRequest bedRequest;
  private static List<String> bloodTypeStrings = Collections.singletonList(bloodType.toString());
  private static PackageRequest packageRequest = mock(PackageRequest.class);
  private static List<PackageRequest> packageRequests = Collections.singletonList(packageRequest);
  private static PackageResponse packageResponse = mock(PackageResponse.class);
  private static List<PackageResponse> packageResponses =
      Collections.singletonList(packageResponse);

  @BeforeAll
  public static void setUpConverter() {
    bedConverter =
        new BedConverter(
            bedNumberConverter, publicKeyConverter, bloodTypesConverter, packageConverter);
  }

  @BeforeEach
  public void setUpMocks() {
    resetMocks();
    when(bedNumberConverter.toString(bedNumber)).thenReturn(bedNumber.toString());
    when(publicKeyConverter.fromString(ownerPublicKey.getValue())).thenReturn(ownerPublicKey);
    when(bloodTypesConverter.fromStrings(bloodTypeStrings)).thenReturn(bloodTypes);
    when(bloodTypesConverter.toStrings(bloodTypes)).thenReturn(bloodTypeStrings);
    when(packageConverter.fromRequests(packageRequests)).thenReturn(pricesPerNight);
    when(packageConverter.toResponses(pricesPerNight)).thenReturn(packageResponses);
  }

  private static void resetMocks() {
    bedNumber = createBedNumber();
    ownerPublicKey = createOwnerPublicKey();
    location = createLocation();
    bedType = createBedType();
    cleaningFrequency = createCleaningFrequency();
    capacity = 100;
    lodgingMode = createLodgingMode();
    stars = 3;

    bed = buildBed();
    bedRequest = buildBedRequest();
  }

  private static Bed buildBed() {
    return aBed()
        .withBedNumber(bedNumber)
        .withOwnerPublicKey(ownerPublicKey)
        .withLocation(location)
        .withBedType(bedType)
        .withCleaningFrequency(cleaningFrequency)
        .withBloodTypes(bloodTypes)
        .withCapacity(capacity)
        .withLodgingMode(lodgingMode)
        .withPricesPerNights(pricesPerNight)
        .build();
  }

  private static BedRequest buildBedRequest() {
    return aBedRequest()
        .withOwnerPublicKey(ownerPublicKey.getValue())
        .withBedType(bedType.toString())
        .withCleaningFrequency(cleaningFrequency.toString())
        .withBloodTypes(convertToStrings(bloodTypes))
        .withCapacity(capacity)
        .withLodgingMode(lodgingMode.toString())
        .build();
  }

  private static List<String> convertToStrings(List<BloodTypes> bloodTypes) {
    return bloodTypes.stream().map(BloodTypes::toString).collect(Collectors.toList());
  }

  @Test
  public void fromRequest_shouldConvertOwnerPublicKey() {
    bed = bedConverter.fromRequest(bedRequest);

    assertEquals(ownerPublicKey, bed.getOwnerPublicKey());
  }

  @Test
  public void fromRequest_shouldConvertBedType() {
    bed = bedConverter.fromRequest(bedRequest);

    assertEquals(bedType, bed.getBedType());
  }

  @Test
  public void fromRequest_withInvalidBedType_shouldThrowInvalidBedTypeException() {
    bedRequest = aBedRequest().withBedType("invalidBedType").build();

    assertThrows(InvalidBedTypeException.class, () -> bedConverter.fromRequest(bedRequest));
  }

  @Test
  public void fromRequest_shouldConvertCleaningFrequency() {
    bed = bedConverter.fromRequest(bedRequest);

    assertEquals(cleaningFrequency, bed.getCleaningFrequency());
  }

  @Test
  public void fromRequest_withInvalidCleaningFrequency_shouldThrowInvalidFormatException() {
    bedRequest = aBedRequest().withCleaningFrequency("invalidCleaningFrequency").build();

    assertThrows(
        InvalidCleaningFrequencyException.class, () -> bedConverter.fromRequest(bedRequest));
  }

  @Test
  public void fromRequest_shouldConvertBloodTypes() {
    bed = bedConverter.fromRequest(bedRequest);

    assertEquals(bloodTypes, bed.getBloodTypes());
  }

  @Test
  public void fromRequest_shouldConvertCapacity() {
    bed = bedConverter.fromRequest(bedRequest);

    assertEquals(capacity, bed.getCapacity());
  }

  @Test
  public void fromRequest_withNegativeCapacity_shouldThrowInvalidCapacityException() {
    bedRequest = aBedRequest().withCapacity(-1).build();

    assertThrows(InvalidCapacityException.class, () -> bedConverter.fromRequest(bedRequest));
  }

  @Test
  public void fromRequest_shouldConvertLodgingMode() {
    bed = bedConverter.fromRequest(bedRequest);

    assertEquals(lodgingMode, bed.getLodgingMode());
  }

  @Test
  public void fromRequest_withoutLodgingMode_shouldConvertPrivateLodgingMode() {
    bedRequest = aBedRequest().withLodgingMode(null).build();

    bed = bedConverter.fromRequest(bedRequest);

    assertEquals(LodgingModes.PRIVATE, bed.getLodgingMode());
  }

  @Test
  public void fromRequest_withInvalidLodgingMode_shouldThrowInvalidLodgingModeException() {
    bedRequest = aBedRequest().withLodgingMode("invalidLodgingMode").build();

    assertThrows(InvalidLodgingModeException.class, () -> bedConverter.fromRequest(bedRequest));
  }

  @Test
  public void fromRequest_shouldConvertPricesPerNight() {
    Bed bed = bedConverter.fromRequest(bedRequest);

    assertEquals(pricesPerNight, bed.getPricesPerNight());
  }

  @Test
  public void toResponseWithoutNumber_shouldNotConvertBedNumber() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertNull(bedResponse.getBedNumber());
  }

  @Test
  public void toResponseWithoutNumber_shouldConvertLocation() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertEquals(location.getZipCode().getValue(), bedResponse.getZipCode());
  }

  @Test
  public void toResponseWithoutNumber_shouldConvertBedType() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertEquals(bedType.toString(), bedResponse.getBedType());
  }

  @Test
  public void toResponseWithoutNumber_shouldConvertCleaningFrequency() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertEquals(cleaningFrequency.toString(), bedResponse.getCleaningFrequency());
  }

  @Test
  public void toResponseWithoutNumber_shouldConvertBloodTypes() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertEquals(bloodTypeStrings, bedResponse.getBloodTypes());
  }

  @Test
  public void toResponseWithoutNumber_shouldConvertCapacity() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertEquals(capacity, bedResponse.getCapacity());
  }

  @Test
  public void toResponseWithoutNumber_shouldConvertLodgingMode() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertEquals(lodgingMode.toString(), bedResponse.getLodgingMode());
  }

  @Test
  public void toResponseWithoutNumber_shouldConvertPricesPerNights() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertEquals(packageResponses, bedResponse.getPackages());
  }

  @Test
  public void toResponseWithoutNumber_shouldConvertStars() {
    BedResponse bedResponse = bedConverter.toResponseWithoutNumber(bed, stars);

    assertEquals(stars, bedResponse.getStars());
  }

  @Test
  public void toResponseWithNumber_shouldConvertBedNumber() {
    BedResponse bedResponse = bedConverter.toResponseWithNumber(bed, stars);

    assertEquals(bedNumber.toString(), bedResponse.getBedNumber());
  }
}
