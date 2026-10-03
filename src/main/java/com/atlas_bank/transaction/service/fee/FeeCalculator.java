package com.atlas_bank.transaction.service.fee;

import com.atlas_bank.account.model.AccountType;
import java.math.BigDecimal;

public interface FeeCalculator {
  boolean supports(AccountType accountType);

  BigDecimal calculate(BigDecimal amount);
}
