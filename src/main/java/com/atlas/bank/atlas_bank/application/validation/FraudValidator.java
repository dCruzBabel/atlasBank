package com.atlas.bank.atlas_bank.application.validation;

import com.atlas.bank.atlas_bank.application.port.out.FraudCheckPort;
import com.atlas.bank.atlas_bank.domain.exception.FraudCheckException;
import com.atlas.bank.atlas_bank.domain.model.shared.FraudCheckResult;
import com.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;
import com.atlas.bank.atlas_bank.domain.validation.TransferValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FraudValidator implements TransferValidator {

  private final FraudCheckPort fraudChecker;

  @Override
  public void validate(TransferContext context) {
    FraudCheckResult fraudCheckResult = fraudChecker.check(context.from().getId(),
        context.amount());
    if (fraudCheckResult.blocked()) {
      throw new FraudCheckException("Transaction blocked due to fraud check: " + fraudCheckResult.reason());
    }

  }
}
