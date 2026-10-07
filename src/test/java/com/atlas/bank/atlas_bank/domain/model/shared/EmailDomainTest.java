package com.atlas.bank.atlas_bank.domain.model.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailDomainTest {

  @Test
  @DisplayName("Debe crear un objeto Email correctamente con un email válido")
  void shouldCreateEmailObjectCorrectly() {
    //Given
    String validEmail = "test@example.com";
    //When
    Email email = Email.of(validEmail);
    //Then
    assertNotNull(email);
    assertEquals(validEmail, email.getValue());
  }

  @Test
  @DisplayName("Debe fallar al crear un objeto Email con un email inválido")
  void shouldFailWhenCreatingEmailWithInvalidEmail() {
    assertThrows(IllegalArgumentException.class, () -> Email.of("invalid-email"));
  }

  @Test
  @DisplayName("Debe normalizar el email a minúsculas al crear un objeto Email")
  void shouldNormalizeEmailToLowerCase() {
    //Given
    String mixedCaseEmail = "Test@Example.Com";
    //When
    Email email = Email.of(mixedCaseEmail);
    //Then
    assertEquals(mixedCaseEmail.toLowerCase(), email.getValue());
  }

  @Test
  @DisplayName("Debe lanzar una excepción al crear un objeto Email con un email nulo")
  void shouldThrowExceptionWhenCreatingEmailWithNull() {
    assertThrows(IllegalArgumentException.class, () -> Email.of(null));
  }

}
