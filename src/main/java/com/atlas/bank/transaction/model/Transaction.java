package com.atlas.bank.transaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String type; // DEPOSIT, WITHDRAWAL, TRANSFER

  private Long sourceAccountId;

  private Long targetAccountId;

  private BigDecimal amount;

  private BigDecimal fee;

  private String status; // PENDING, EXECUTED, REJECTED

  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    if (this.status == null) this.status = "EXECUTED";
  }
}