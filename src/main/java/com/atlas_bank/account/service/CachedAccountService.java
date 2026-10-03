package com.atlas_bank.account.service;

import com.atlas_bank.account.model.Account;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
//@Component
//@Primary
public class CachedAccountService implements IAccountService {

  private final IAccountService delegate;
  private final Map<Long, Account> cache = new ConcurrentHashMap<>();

  public CachedAccountService(@Qualifier("auditableAccountService") IAccountService delegate) {
    this.delegate = delegate;
  }

  @Override
  public Account create(Account account) {

    Account created = delegate.create(account);
    cache.put(created.getId(), created);
    log.info("Created account {}", created);
    return created;
  }

  @Override
  public List<Account> findAll() {
    return delegate.findAll();
  }

  @Override
  public Account findById(Long id) {
    Account cached = cache.get(id);

    if (cached != null) {
      log.info("Found account {} in cache", cached);
      return cached;
    }
    log.info("Account with id {} not found in cache, delegating to underlying service", id);
    Account found = delegate.findById(id);
    if (found != null) {
      cache.put(id, found);
    }
    return found;
  }
}
