package com.atlas.bank.transaction.validation;

import com.atlas.bank.transaction.dto.TransferRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentAccountsValidator implements ConstraintValidator<DifferentAccounts,
    TransferRequest> {
  @Override
  public boolean isValid(TransferRequest transferRequest, ConstraintValidatorContext context) {
    if (transferRequest == null) {
      return true; // No validation needed if transferRequest is null
    }
    Long fromAccount = transferRequest.getSourceAccountId();
    Long toAccount = transferRequest.getTargetAccountId();

    return !fromAccount.equals(toAccount);
  }
}
