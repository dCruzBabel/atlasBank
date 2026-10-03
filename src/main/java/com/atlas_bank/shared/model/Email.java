package com.atlas_bank.shared.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Email {

  @Column(nullable = false)
  private String address;

  private Email(String address) {
    this.address = address.toLowerCase().trim();
  }

  public static Email of(String address) {
    if (address == null || address.isBlank()) {
      throw new IllegalArgumentException("Email address cannot be null or blank");
    }
    if (!address.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
      throw new IllegalArgumentException("Invalid email address format");
    }
    return new Email(address);
  }

  public String toString() {
    return address;
  }
}
