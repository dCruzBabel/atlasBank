package com.atlas_bank.account.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(Long id) {
    super("Account with ID " + id + " not found.");
  }
}
