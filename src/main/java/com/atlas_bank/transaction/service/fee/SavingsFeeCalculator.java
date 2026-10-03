package com.atlas_bank.transaction.service.fee;

import com.atlas_bank.account.model.AccountType;
import java.math.BigDecimal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SavingsFeeCalculator implements FeeCalculator {
  @Override
  public boolean supports(AccountType accountType) {
    return AccountType.SAVINGS.equals(accountType);
  }

  @Override
  public BigDecimal calculate(BigDecimal amount) {
    return amount.multiply(BigDecimal.valueOf(0.01)); // 1% fee for savings accounts
  }
}
