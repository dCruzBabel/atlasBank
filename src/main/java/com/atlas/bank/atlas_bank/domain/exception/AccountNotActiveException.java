package com.atlas.bank.atlas_bank.domain.exception;

public class AccountNotActiveException extends RuntimeException {

  public AccountNotActiveException(Long accountId, String status) {
    super("Account with ID " + accountId + " is not active. Current status: " + status);
  }
}
