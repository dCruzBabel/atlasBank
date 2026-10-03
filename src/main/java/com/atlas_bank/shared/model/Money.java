package com.atlas_bank.shared.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Money {

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 3)
  private Currency currency;

  private Money(BigDecimal amount, Currency currency) {

    if (amount == null || currency == null) {
      throw new IllegalArgumentException("Amount and currency must not be null");
    }

    this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    this.currency = currency;
  }

  public static Money of(BigDecimal amount, Currency currency) {
    return new Money(amount, currency);
  }

  public static Money zero(Currency currency) {
    return new Money(BigDecimal.ZERO, currency);
  }

  public Money add(Money other) {
    validateSameCurrency(other);
    return Money.of(this.amount.add(other.amount), this.currency);
  }

  public Money subtract(Money other) {
    validateSameCurrency(other);
    return Money.of(this.amount.subtract(other.amount), this.currency);
  }

  public boolean isGreaterThan(Money other) {
    validateSameCurrency(other);
    return this.amount.compareTo(other.amount) > 0;
  }

  public boolean isLessThan(Money other) {
    validateSameCurrency(other);
    return this.amount.compareTo(other.amount) < 0;
  }

  public boolean isNegative() {
    return this.amount.compareTo(BigDecimal.ZERO) < 0;
  }

  private void validateSameCurrency(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot operate on Money with different currencies" +
          " This: " + this.currency + ", Other: " + other.currency);
    }
  }

  @Override
  public String toString() {
    return amount.toPlainString() + " " + currency;
  }
}
