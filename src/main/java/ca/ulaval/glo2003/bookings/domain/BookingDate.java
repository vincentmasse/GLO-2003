package ca.ulaval.glo2003.bookings.domain;

import java.time.LocalDate;

public class BookingDate {

  private LocalDate value;

  public static BookingDate now() {
    return new BookingDate(LocalDate.now());
  }

  public BookingDate(LocalDate value) {
    this.value = value;
  }

  public LocalDate getValue() {
    return value;
  }

  public BookingDate plusDays(int days) {
    return new BookingDate(value.plusDays(days));
  }

  public boolean isAfter(BookingDate other) {
    return value.isAfter(other.getValue());
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || getClass() != object.getClass()) return false;

    BookingDate bookingDate = (BookingDate) object;

    return value.equals(bookingDate.getValue());
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}