package com.atlas.bank.transaction.dto;

import com.atlas.bank.transaction.validation.DifferentAccounts;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

@Data
@DifferentAccounts
public class TransferRequest {

  @NotNull(message = "Source account ID is required")
  private Long sourceAccountId;

  @NotNull(message = "Target account ID is required")
  private Long targetAccountId;

  @NotNull(message = "Amount is required")
  @PositiveOrZero(message = "Amount must be positive")
  private BigDecimal amount;
}
