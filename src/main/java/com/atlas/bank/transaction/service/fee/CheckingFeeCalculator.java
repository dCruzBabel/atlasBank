package com.atlas.bank.transaction.service.fee;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class CheckingFeeCalculator implements FeeCalculator {

  @Override
  public boolean supports(String accountType) {
    return "CHECKING".equalsIgnoreCase(accountType);
  }

  @Override
  public BigDecimal calculate(BigDecimal amount) {
    return amount.multiply(BigDecimal.valueOf(0.015)); // 1.5% fee for checking accounts
  }

}
