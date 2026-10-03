package com.atlas_bank.account.model;

import com.atlas_bank.shared.model.Currency;
import com.atlas_bank.shared.model.Email;
import com.atlas_bank.shared.model.Money;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Service
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "account_number", nullable = false, unique = true)
  private String accountNumber;

  @Column(name = "owner_name", nullable = false)
  private String ownerName;

  @Embedded
  @AttributeOverride(name = "address", column = @Column(name = "email", nullable = false))
  private Email email;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private AccountType type;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "amount", column = @Column(name = "amount", nullable = false)),
      @AttributeOverride(name = "currency", column = @Column(name = "currency", nullable = false,
          length = 3))
  })
  private Money balance;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private AccountStatus status;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "customer_id", nullable = false)
  private Long customerId;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    if (status == null) status = AccountStatus.ACTIVE;
    if (balance == null) balance = Money.zero(Currency.USD);
  }

  public void deposit(Money money) {
    if (money == null || money.isNegative()) {
      throw new IllegalArgumentException("Deposit amount must be positive");
    }
    this.balance = this.balance.add(money);
  }

  public void withdraw(Money money) {
    if (money == null || money.isNegative()) {
      throw new IllegalArgumentException("Withdrawal amount must be positive");
    }
    if (this.balance.isLessThan(money)) {
      throw new IllegalArgumentException("Insufficient funds for withdrawal");
    }
    this.balance = this.balance.subtract(money);
  }

}
