package com.atlas.bank.atlas_bank.domain.exception;

import com.atlas.bank.atlas_bank.domain.model.account.AccountStatus;

public class AccountNotActiveException extends RuntimeException {

  public AccountNotActiveException(Long accountId, AccountStatus status) {
    super("Account with ID " + accountId + " is not active. Current status: " + status);
  }
}
