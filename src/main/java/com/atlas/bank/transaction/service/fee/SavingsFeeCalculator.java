package com.atlas.bank.transaction.service.fee;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class SavingsFeeCalculator implements FeeCalculator {
  @Override
  public boolean supports(String accountType) {
    return "SAVINGS".equalsIgnoreCase(accountType);
  }

  @Override
  public BigDecimal calculate(BigDecimal amount) {
    return amount.multiply(BigDecimal.valueOf(0.01)); // 1% fee for savings accounts
  }
}
