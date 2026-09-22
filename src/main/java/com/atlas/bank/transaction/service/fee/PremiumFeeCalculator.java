package com.atlas.bank.transaction.service.fee;

import java.math.BigDecimal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class PremiumFeeCalculator implements FeeCalculator {
  @Override
  public boolean supports(String accountType) {
    return "PREMIUM".equalsIgnoreCase(accountType);
  }

  @Override
  public BigDecimal calculate(BigDecimal amount) {
    return BigDecimal.ZERO; // No fee for premium accounts
  }
}
