package com.atlas.bank.transaction.service.event;

import com.atlas.bank.transaction.model.TransactionType;
import java.math.BigDecimal;

public record TransactionExecutedEvent(
    Long transactionId,
    TransactionType type,
    Long sourceAccountId,
    Long targetAccountId,
    BigDecimal amount,
    BigDecimal fee) {


}
