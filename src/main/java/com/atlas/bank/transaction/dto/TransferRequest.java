package com.atlas.bank.transaction.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransferRequest {

  private Long sourceAccountId;

  private Long targetAccountId;

  private BigDecimal amount;
}
