package com.atlas.bank.atlas_bank.domain.model.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.atlas_bank.domain.model.shared.Currency;
import com.atlas.bank.atlas_bank.domain.model.shared.Email;
import com.atlas.bank.atlas_bank.domain.model.shared.Money;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountDomainTest {

  private Account createAccountWithBalance(BigDecimal balance) {
    return Account.builder()
        .id(1L)
        .accountNumber("123456789")
        .ownerName("John Doe")
        .email(Email.of("john.doe@example.com"))
        .type(AccountType.SAVINGS)
        .balance(Money.of(balance, Currency.USD))
        .status(AccountStatus.ACTIVE)
        .build();
  }

  @Test
  @DisplayName("Debe depositar dinero en la cuenta correctamente")
  void shouldDepositMoneyCorrectly() {
    //Given
    Account account = createAccountWithBalance(BigDecimal.valueOf(100));
    Money depositAmount = Money.of(BigDecimal.valueOf(50), Currency.USD);
    //When
    account.deposit(depositAmount);
    //Then
    assertEquals(Money.of(BigDecimal.valueOf(150), Currency.USD), account.getBalance());
  }

  @Test
  @DisplayName("Debe retirar dinero de la cuenta correctamente")
  void shouldWithdrawMoneyCorrectly() {
    //Given
    Account account = createAccountWithBalance(BigDecimal.valueOf(100));
    Money withdrawAmount = Money.of(BigDecimal.valueOf(50), Currency.USD);
    //When
    account.withdraw(withdrawAmount);
    //Then
    assertEquals(Money.of(BigDecimal.valueOf(50), Currency.USD), account.getBalance());
  }

  @Test
  @DisplayName("Debe lanzar una excepción al intentar retirar más dinero del disponible")
  void shouldThrowExceptionWhenWithdrawingMoreThanAvailable() {
    //Given
    Account account = createAccountWithBalance(BigDecimal.valueOf(100));
    Money withdrawAmount = Money.of(BigDecimal.valueOf(150), Currency.USD);
    //When & Then
    assertThrows(InsufficientFundsException.class, () -> account.withdraw(withdrawAmount));
  }

  @Test
  @DisplayName("Debe rechazar deposito con cantidad negativa")
  void shouldRejectDepositWithNegativeAmount() {
    //Given
    Account account = createAccountWithBalance(BigDecimal.valueOf(100));
    Money depositAmount = Money.of(BigDecimal.valueOf(-50), Currency.USD);
    //When & Then
    assertThrows(IllegalArgumentException.class, () -> account.deposit(depositAmount));
  }

  @Test
  @DisplayName("Debe inicializar con valores por defecto correctamente")
  void shouldInitializeWithDefaultValuesCorrectly() {
    //Given
    Account account = Account.builder()
        .id(1L)
        .accountNumber("123456789")
        .ownerName("John Doe")
        .email(Email.of("john.doe@example.com"))
        .type(AccountType.SAVINGS)
        .build();

    //When
    account.initDefaults();

    //Then
    assertEquals(AccountStatus.ACTIVE, account.getStatus());
    assertEquals(Money.zero(Currency.ARS), account.getBalance());
    assertNotNull(account.getCreatedAt());

  }


}
