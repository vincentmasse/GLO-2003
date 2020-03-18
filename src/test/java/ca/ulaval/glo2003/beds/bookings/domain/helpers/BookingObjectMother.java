package ca.ulaval.glo2003.beds.bookings.domain.helpers;

import static ca.ulaval.glo2003.beds.domain.helpers.PublicKeyObjectMother.createPublicKey;
import static ca.ulaval.glo2003.interfaces.helpers.Randomizer.randomEnum;

import ca.ulaval.glo2003.beds.bookings.domain.BookingDate;
import ca.ulaval.glo2003.beds.domain.Packages;
import ca.ulaval.glo2003.beds.domain.PublicKey;
import ca.ulaval.glo2003.transactions.domain.Price;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.UUID;

public class BookingObjectMother {

  private BookingObjectMother() {}

  public static UUID createBookingNumber() {
    return UUID.randomUUID();
  }

  public static PublicKey createTenantPublicKey() {
    return createPublicKey();
  }

  public static Price createTotal() {
    double randomDouble = Faker.instance().number().randomDouble(2, 100, 1000);
    return new Price(BigDecimal.valueOf(randomDouble));
  }

  public static BookingDate createArrivalDate() {
    return new BookingDate(
        Faker.instance()
            .date()
            .birthday()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate());
  }

  public static int createNumberOfNights() {
    return Faker.instance().number().numberBetween(1, 5);
  }

  public static Packages createPackageName() {
    return randomEnum(Packages.class);
  }
}
