package com.atlas_bank.transaction.validation.chain;

import com.atlas_bank.account.model.AccountStatus;
import com.atlas_bank.transaction.exception.AccountNotActiveException;
import com.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AccountStatusValidator implements TransferValidator {
  @Override
  public void validate(TransferContext context) {
    if (!AccountStatus.ACTIVE.equals(context.from().getStatus())) {
      throw new AccountNotActiveException(context.from().getId(), context.from().getStatus());
    }
    if (!AccountStatus.ACTIVE.equals(context.to().getStatus())) {
      throw new AccountNotActiveException(context.to().getId(), context.to().getStatus());
    }

  }
}
