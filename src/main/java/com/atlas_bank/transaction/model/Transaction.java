package com.atlas_bank.transaction.model;

import com.atlas_bank.transaction.model.state.ExecutedState;
import com.atlas_bank.transaction.model.state.PendingState;
import com.atlas_bank.transaction.model.state.RejectedState;
import com.atlas_bank.transaction.model.state.ReversedState;
import com.atlas_bank.transaction.model.state.TransactionState;
import com.atlas_bank.transaction.model.state.ValidatedState;
import com.atlas_bank.transaction.service.event.TransactionExecutedEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Transaction extends AbstractAggregateRoot<Transaction> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  @EqualsAndHashCode.Include
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionType type;

  @Column(name = "source_account_id", nullable = false)
  private Long sourceAccountId;

  @Column(name = "target_account_id", nullable = false)
  private Long targetAccountId;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = false)
  private BigDecimal fee;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionStatus status;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
  @Transient
  private TransactionState state;

  public TransactionState getState() {
    if (state == null) {
      state = switch (status) {
        case PENDING -> new PendingState();
        case VALIDATED -> new ValidatedState();
        case EXECUTED -> new ExecutedState();
        case REJECTED -> new RejectedState();
        case REVERSED -> new ReversedState();
      };
    }
    return state;
  }

  public void advanceTo(TransactionState newState) {
    this.state = newState;
    this.status = newState.status();
  }

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    if (this.status == null) this.status = TransactionStatus.EXECUTED;
  }

  public void markAsExecuted() {
    registerEvent(new TransactionExecutedEvent(
        id,
        type,
        sourceAccountId,
        targetAccountId,
        amount,
        fee
    ));
  }

  public void executeTransfer() {
    this.advanceTo(getState().validate());
    this.advanceTo(getState().execute());
    this.markAsExecuted();
  }
}