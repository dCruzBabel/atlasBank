package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.transaction.exception.InsufficientFundsException;
import com.atlas.bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SufficientFundsValidator implements TransferValidator {
  @Override
  public void validate(TransferContext context) {
    if (context.from().getBalance().compareTo(context.amount()) < 0) {
      throw new InsufficientFundsException(context.from().getId(), context.from().getBalance(),
          context.amount());
    }
  }
}
