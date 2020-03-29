package ca.ulaval.glo2003.bookings.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BookingDateTest {

  private static BookingDate bookingDate;

  private static LocalDate date = LocalDate.now();
  private static LocalDate otherDate = date.plusDays(1);

  @BeforeAll
  public static void setUpDate() {
    bookingDate = new BookingDate(date);
  }

  @Test
  public void now_shouldSetValueToNow() {
    LocalDate now = LocalDate.now();

    bookingDate = BookingDate.now();

    assertEquals(now, bookingDate.getValue());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 3, 5})
  public void plusDays_shouldReturnBookingDatePlusDays(int days) {
    LocalDate plusDays = date.plusDays(days);

    BookingDate actualBookingDate = bookingDate.plusDays(days);

    assertEquals(plusDays, actualBookingDate.getValue());
  }

  @Test
  public void isAfter_withDateBefore_shouldReturnTrue() {
    BookingDate dateBefore = new BookingDate(date.minusDays(1));

    boolean result = bookingDate.isAfter(dateBefore);

    assertTrue(result);
  }

  @Test
  public void isAfter_withDateEquals_shouldReturnFalse() {
    BookingDate dateEqual = new BookingDate(date);

    boolean result = bookingDate.isAfter(dateEqual);

    assertFalse(result);
  }

  @Test
  public void isAfter_withDateAfter_shouldReturnFalse() {
    BookingDate dateAfter = new BookingDate(date.plusDays(1));

    boolean result = bookingDate.isAfter(dateAfter);

    assertFalse(result);
  }

  @Test
  public void equals_shouldReturnFalse_whenObjectIsNotBookingDate() {
    Object object = new Object();

    boolean result = bookingDate.equals(object);

    assertFalse(result);
  }

  @Test
  public void equals_shouldReturnFalse_whenValuesAreNotEqual() {
    BookingDate otherBookingDate = new BookingDate(otherDate);

    boolean result = bookingDate.equals(otherBookingDate);

    assertFalse(result);
  }

  @Test
  public void equals_shouldReturnTrue_whenValuesAreEqual() {
    BookingDate otherBookingDate = new BookingDate(date);

    boolean result = bookingDate.equals(otherBookingDate);

    assertTrue(result);
  }
}