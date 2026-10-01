package com.atlas.bank.transaction.service.fee;

import com.atlas.bank.account.model.AccountType;
import java.math.BigDecimal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class PremiumFeeCalculator implements FeeCalculator {
  @Override
  public boolean supports(AccountType accountType) {
    return AccountType.PREMIUM.equals(accountType);
  }

  @Override
  public BigDecimal calculate(BigDecimal amount) {
    return BigDecimal.ZERO; // No fee for premium accounts
  }
}
