package com.atlas_bank.transaction.service.domain;

import com.atlas_bank.account.model.Account;
import com.atlas_bank.shared.model.Money;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class TransferDomainService {

  public void transfer(Account from, Account to, BigDecimal amount, BigDecimal fee) {

    Money totalDebit = Money.of(amount.add(fee), from.getBalance().getCurrency());
    Money depositAmount = Money.of(amount, to.getBalance().getCurrency());

    from.withdraw(totalDebit);
    to.deposit(depositAmount);

  }
}
