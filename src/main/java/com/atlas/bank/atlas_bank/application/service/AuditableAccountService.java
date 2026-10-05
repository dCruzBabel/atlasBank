package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.ListAccountUseCase;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements CreateAccountUseCase, ListAccountUseCase,
    GetAccountUseCase {

  private final CreateAccountUseCase createAccountUseCase;
  private final ListAccountUseCase listAccountUseCase;
  private final GetAccountUseCase getAccountUseCase;

  public AuditableAccountService(@Qualifier("accountService") CreateAccountUseCase createAccountUseCase,
      @Qualifier("accountService") ListAccountUseCase listAccountUseCase,
      @Qualifier("accountService") GetAccountUseCase getAccountUseCase) {
    this.createAccountUseCase = createAccountUseCase;
    this.listAccountUseCase = listAccountUseCase;
    this.getAccountUseCase = getAccountUseCase;
  }

  @Override
  public Account create(Account account) {

    log.info("Creating account {}", account);
    Account createdAccount = createAccountUseCase.create(account);
    log.info("Created account {}", createdAccount);

    return createdAccount;
  }

  @Override
  public List<Account> findAll() {
    log.info("Finding all accounts");
    List<Account> accounts = listAccountUseCase.findAll();
    log.info("Found accounts {}", accounts);
    return accounts;
  }

  @Override
  public Account findById(Long id) {
    log.info("Finding account by id {}", id);
    Account account = getAccountUseCase.findById(id);
    log.info("Found account {}", account);
    return account;
  }
}
