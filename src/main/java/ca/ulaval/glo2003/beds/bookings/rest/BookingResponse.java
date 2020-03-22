package ca.ulaval.glo2003.beds.bookings.rest;

import ca.ulaval.glo2003.beds.domain.Packages;
import ca.ulaval.glo2003.beds.rest.mappers.PriceJsonSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BookingResponse {

  private String arrivalDate;
  private int numberOfNights;
  private String status;

  @JsonProperty("package")
  private Packages bookingPackage;

  @JsonSerialize(using = PriceJsonSerializer.class)
  private Double total;

  public BookingResponse(
      String arrivalDate,
      int numberOfNights,
      Packages bookingPackage,
      Double total,
      String status) {
    this.arrivalDate = arrivalDate;
    this.numberOfNights = numberOfNights;
    this.bookingPackage = bookingPackage;
    this.total = total;
    this.status = status;
  }

  public String getArrivalDate() {
    return arrivalDate;
  }

  public int getNumberOfNights() {
    return numberOfNights;
  }

  public Packages getBookingPackage() {
    return bookingPackage;
  }

  public Double getTotal() {
    return total;
  }

  public String getStatus() {
    return status;
  }
}
