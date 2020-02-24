package ca.ulaval.glo2003.beds.helpers;

import static ca.ulaval.glo2003.interfaces.helpers.Randomizer.randomEnum;

import ca.ulaval.glo2003.beds.domain.*;
import ca.ulaval.glo2003.beds.domain.Package;
import ca.ulaval.glo2003.beds.rest.mappers.BedMapper;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import java.math.BigDecimal;
import java.util.*;

public class BedObjectMother {

  // TODO : This is also used in BedRequestObjectMother. Duplicated code is bad.
  private static final FakeValuesService fakeValuesService =
      new FakeValuesService(new Locale("en-US"), new RandomService());

  private BedObjectMother() {}

  public static String createOwnerPublicKey() {
    return fakeValuesService.regexify(BedMapper.OWNER_PUBLIC_KEY_PATTERN);
  }

  public static String createZipCode() {
    return Faker.instance().address().zipCode();
  }

  public static BedTypes createBedType() {
    return randomEnum(BedTypes.class);
  }

  public static CleaningFrequencies createCleaningFrequency() {
    return randomEnum(CleaningFrequencies.class);
  }

  public static List<BloodTypes> createBloodTypes() {
    return Collections.singletonList(randomEnum(BloodTypes.class));
  }

  public static int createCapacity() {
    return Faker.instance().number().numberBetween(1, 1000);
  }

  public static List<Package> createPackages() {
    return Collections.singletonList(
        new Package(createPackageName(), createPackagePricePerNight()));
  }

  private static PackageNames createPackageName() {
    return randomEnum(PackageNames.class);
  }

  private static BigDecimal createPackagePricePerNight() {
    double randomDouble = Faker.instance().number().randomDouble(2, 100, 1000);
    return BigDecimal.valueOf(randomDouble);
  }
}
