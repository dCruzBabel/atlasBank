package com.atlas.bank.transaction.service.fee;

import com.atlas.bank.account.model.AccountType;
import java.math.BigDecimal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CheckingFeeCalculator implements FeeCalculator {

  @Override
  public boolean supports(AccountType accountType) {
    return AccountType.CHECKING.equals(accountType);
  }

  @Override
  public BigDecimal calculate(BigDecimal amount) {
    return amount.multiply(BigDecimal.valueOf(0.015)); // 1.5% fee for checking accounts
  }

}
