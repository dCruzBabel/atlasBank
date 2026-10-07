package com.atlas.bank.atlas_bank.domain.validation;

import com.atlas.bank.atlas_bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.domain.model.account.AccountStatus;
import com.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;

public class AccountStatusValidator implements TransferValidator {
  @Override
  public void validate(TransferContext context) {
    if (!AccountStatus.ACTIVE.equals(context.from().getStatus())) {
      throw new AccountNotActiveException(context.from().getId(), context.from().getStatus().name());
    }
    if (!AccountStatus.ACTIVE.equals(context.to().getStatus())) {
      throw new AccountNotActiveException(context.to().getId(), context.to().getStatus().name());
    }

  }
}
