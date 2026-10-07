package com.atlas.bank.atlas_bank.domain.model.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyDomainTest {

  @Test
  @DisplayName("Debe sumar dos cantidades de la misma moneda correctamente")
  void shouldAddMoneyCorrectly() {
    //Given
    Money money1 = Money.of(BigDecimal.valueOf(100), Currency.USD);
    Money money2 = Money.of(BigDecimal.valueOf(50), Currency.USD);
    //When
    Money result = money1.add(money2);
    //Then
    assertEquals(Money.of(BigDecimal.valueOf(150), Currency.USD), result);
  }

  @Test
  @DisplayName("Debe fallar al sumar dos cantidades de diferentes monedas")
  void shouldFailWhenAddingDifferentCurrencies() {
    //Given
    Money money1 = Money.of(BigDecimal.valueOf(100), Currency.USD);
    Money money2 = Money.of(BigDecimal.valueOf(50), Currency.ARS);
    //When & Then
    assertThrows(IllegalArgumentException.class, () -> money1.add(money2));
  }

  @Test
  @DisplayName("Verificar que el método isNegative() funciona correctamente")
  void shouldCheckIfMoneyIsNegative() {
    //Given
    Money positiveMoney = Money.of(BigDecimal.valueOf(100), Currency.USD);
    Money negativeMoney = Money.of(BigDecimal.valueOf(-50), Currency.USD);
    //Then
    assertFalse(positiveMoney.isNegative());
    assertTrue(negativeMoney.isNegative());
  }

  @Test
  @DisplayName("Debe comparar correctamente dos cantidades de dinero con la misma moneda")
  void shouldCompareMoneyCorrectly() {
    //Given
    Money money1 = Money.of(BigDecimal.valueOf(100), Currency.USD);
    Money money2 = Money.of(BigDecimal.valueOf(50), Currency.USD);
    //Then
    assertTrue(money1.isGreaterThan(money2));
    assertTrue(money2.isLessThan(money1));
  }

  @Test
  @DisplayName("Money.zero() debe crear una cantidad de dinero con valor cero y la moneda especificada")
  void shouldCreateZeroMoneyWithSpecifiedCurrency() {
    //Given
    Currency currency = Currency.USD;
    //When
    Money zeroMoney = Money.zero(currency);
    //Then
    assertEquals(Money.of(BigDecimal.ZERO, currency), zeroMoney);
  }


}