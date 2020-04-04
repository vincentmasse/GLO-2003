package ca.ulaval.glo2003.transactions.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PriceTest {

  private static Price price;

  @BeforeEach
  public void setUpPrice() {
    price = new Price(BigDecimal.valueOf(100));
  }

  @Test
  public void multiply_shouldMultiplyValue() {
    BigDecimal multipliedValue = BigDecimal.valueOf(500);

    price = price.multiply(BigDecimal.valueOf(5));

    assertEquals(multipliedValue, price.getValue());
  }

  @Test
  public void divide_shouldDivideValue() {
    BigDecimal expectedMultipliedValue = BigDecimal.valueOf(20);

    price = price.divide(BigDecimal.valueOf(5), RoundingMode.DOWN);

    assertEquals(expectedMultipliedValue, price.getValue());
  }

  @ParameterizedTest
  @MethodSource("provideDividedValues")
  public void divide_shouldRoundValue(
      BigDecimal divisor, RoundingMode roundingMode, BigDecimal dividedValue) {
    price = price.divide(divisor, roundingMode);

    assertEquals(dividedValue, price.getValue());
  }

  private static Stream<Arguments> provideDividedValues() {
    return Stream.of(
        Arguments.of(BigDecimal.valueOf(3), RoundingMode.DOWN, BigDecimal.valueOf(33)),
        Arguments.of(BigDecimal.valueOf(3), RoundingMode.UP, BigDecimal.valueOf(34)));
  }

  @Test
  public void equals_shouldReturnFalse_whenObjectIsNotPrice() {
    Object object = new Object();

    boolean result = price.equals(object);

    assertFalse(result);
  }

  @Test
  public void equals_shouldReturnFalse_whenValuesAreNotEqual() {
    Price otherPrice = new Price(BigDecimal.ONE);

    boolean result = price.equals(otherPrice);

    assertFalse(result);
  }

  @Test
  public void equals_shouldReturnTrue_whenValuesAreEqual() {
    Price otherPrice = new Price(price.getValue());

    boolean result = price.equals(otherPrice);

    assertTrue(result);
  }
}
