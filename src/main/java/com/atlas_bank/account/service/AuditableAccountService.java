package com.atlas_bank.account.service;

import com.atlas_bank.account.model.Account;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements IAccountService {

  private final IAccountService delegate;

  public AuditableAccountService(@Qualifier("accountService") IAccountService delegate) {
    this.delegate = delegate;
  }

  @Override
  public Account create(Account account) {

    log.info("Creating account {}", account);
    Account createdAccount = delegate.create(account);
    log.info("Created account {}", createdAccount);

    return createdAccount;
  }

  @Override
  public List<Account> findAll() {
    log.info("Finding all accounts");
    List<Account> accounts = delegate.findAll();
    log.info("Found accounts {}", accounts);
    return accounts;
  }

  @Override
  public Account findById(Long id) {
    log.info("Finding account by id {}", id);
    Account account = delegate.findById(id);
    log.info("Found account {}", account);
    return account;
  }
}
