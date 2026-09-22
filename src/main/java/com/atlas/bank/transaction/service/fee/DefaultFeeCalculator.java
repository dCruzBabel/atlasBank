package com.atlas.bank.transaction.service.fee;

import java.math.BigDecimal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order()
public class DefaultFeeCalculator implements FeeCalculator {

  @Override
  public boolean supports(String accountType) {
    return true; // Default calculator supports all account types
  }

  @Override
  public BigDecimal calculate(BigDecimal amount) {
    return BigDecimal.ZERO; // No fee for unsupported account types
  }

}
