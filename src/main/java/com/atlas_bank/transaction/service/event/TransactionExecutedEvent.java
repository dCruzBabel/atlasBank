package com.atlas_bank.transaction.service.event;

import com.atlas_bank.transaction.model.TransactionType;
import java.math.BigDecimal;

public record TransactionExecutedEvent(
    Long transactionId,
    TransactionType type,
    Long sourceAccountId,
    Long targetAccountId,
    BigDecimal amount,
    BigDecimal fee) {


}
