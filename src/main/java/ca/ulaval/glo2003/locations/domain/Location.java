package ca.ulaval.glo2003.locations.domain;

public class Location {

  private String zipCode;
  private Coordinates coordinates;

  public Location(String zipCode, Coordinates coordinates) {
    this.zipCode = zipCode;
    this.coordinates = coordinates;
  }

  public String getZipCode() {
    return zipCode;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public boolean isWithinRadius(Location origin, double maxDistance) {
    double distance = coordinates.calculateDistance(origin.getCoordinates());
    return distance <= maxDistance;
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || getClass() != object.getClass()) return false;

    Location location = (Location) object;

    return zipCode.equals(location.getZipCode());
  }

  @Override
  public int hashCode() {
    return zipCode.hashCode();
  }
}
