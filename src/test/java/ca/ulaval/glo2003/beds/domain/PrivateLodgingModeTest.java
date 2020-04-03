package ca.ulaval.glo2003.beds.domain;

import static ca.ulaval.glo2003.beds.domain.helpers.BedObjectMother.createCapacity;
import static ca.ulaval.glo2003.bookings.domain.helpers.BookingObjectMother.createArrivalDate;
import static ca.ulaval.glo2003.bookings.domain.helpers.BookingObjectMother.createNumberOfNights;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.bookings.domain.Booking;
import ca.ulaval.glo2003.bookings.domain.BookingDate;
import ca.ulaval.glo2003.transactions.domain.Price;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrivateLodgingModeTest {

  private static LodgingMode privateLodgingMode;
  private static Bed bed = mock(Bed.class);
  private static Booking booking = mock(Booking.class);
  private static Integer minCapacity;
  private static BookingDate arrivalDate;
  private static int numberOfNights;
  private static List<Booking> bookings;
  private static Price total;

  @BeforeAll
  public static void setUpLodgingMode() {
    privateLodgingMode = new PrivateLodgingMode();
  }

  @BeforeEach
  public void setUpMocks() {
    minCapacity = createCapacity();
    arrivalDate = createArrivalDate();
    numberOfNights = createNumberOfNights();
    bookings = Collections.emptyList();

    setUpCalculator();

    resetMocks();
  }

  public void setUpCalculator() {
    total = new Price(BigDecimal.valueOf(100));
  }

  private void setUpWithBookings() {
    bookings = Collections.singletonList(booking);
    resetMocks();
  }

  private void resetMocks() {
    resetBooking();
    resetBed();
  }

  private void resetBed() {
    reset(bed);
    when(bed.getBookings()).thenReturn(bookings);
  }

  private void resetBooking() {
    reset(booking);
    when(booking.getColonySize()).thenReturn(minCapacity);
    when(booking.getArrivalDate()).thenReturn(arrivalDate);
    when(booking.getNumberOfNights()).thenReturn(numberOfNights);
    when(booking.isOverlapping(arrivalDate, numberOfNights)).thenReturn(false);
  }

  @Test
  public void isAvailable_withNoBooking_shouldReturnTrue() {
    boolean result = privateLodgingMode.isAvailable(bed, minCapacity, arrivalDate, numberOfNights);

    assertTrue(result);
  }

  @Test
  public void isAvailable_withoutOverlappingBooking_shouldReturnTrue() {
    setUpWithBookings();

    boolean result = privateLodgingMode.isAvailable(bed, minCapacity, arrivalDate, numberOfNights);

    assertTrue(result);
  }

  @Test
  public void isAvailable_withOverlappingBooking_shouldReturnFalse() {
    setUpWithBookings();
    when(booking.isOverlapping(arrivalDate, numberOfNights)).thenReturn(true);

    boolean result = privateLodgingMode.isAvailable(bed, minCapacity, arrivalDate, numberOfNights);

    assertFalse(result);
  }

  @Test
  public void applyDiscount_shouldReturnCorrectTotal() {

    Price expectedTotal = new Price(BigDecimal.valueOf(100));

    // assertEquals(expectedTotal, privateLodgingMode.applyDiscount(total,booking,bed).getValue());
  }

  @Test
  public void getName_shouldReturnPrivate() {
    assertEquals(LodgingModes.PRIVATE, privateLodgingMode.getName());
  }
}
