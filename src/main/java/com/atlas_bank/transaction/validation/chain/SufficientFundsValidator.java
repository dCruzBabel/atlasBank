package com.atlas_bank.transaction.validation.chain;

import com.atlas_bank.transaction.exception.InsufficientFundsException;
import com.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SufficientFundsValidator implements TransferValidator {
  @Override
  public void validate(TransferContext context) {
    if (context.from().getBalance().getAmount().compareTo(context.amount()) < 0) {
      throw new InsufficientFundsException(context.from().getId(),
          context.from().getBalance().getAmount(),
          context.amount());
    }
  }
}
