package ca.ulaval.glo2003.beds.rest;

public class PackageRequest {

  private String name;
  private double pricePerNight;

  public PackageRequest() {
    // Empty constructor for parsing
  }

  public PackageRequest(String name, double pricePerNight) {
    this.name = name;
    this.pricePerNight = pricePerNight;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPricePerNight() {
    return pricePerNight;
  }

  public void setPricePerNight(double pricePerNight) {
    this.pricePerNight = pricePerNight;
  }
}
