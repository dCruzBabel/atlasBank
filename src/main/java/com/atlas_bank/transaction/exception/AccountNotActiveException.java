package com.atlas_bank.transaction.exception;

import com.atlas_bank.account.model.AccountStatus;

public class AccountNotActiveException extends RuntimeException {

  public AccountNotActiveException(Long accountId, AccountStatus status) {
    super("Account with ID " + accountId + " is not active. Current status: " + status);
  }
}
