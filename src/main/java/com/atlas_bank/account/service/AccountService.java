package com.atlas_bank.account.service;

import com.atlas_bank.account.exception.AccountNotFoundException;
import com.atlas_bank.account.model.Account;
import com.atlas_bank.account.repository.DomainAccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

  private final DomainAccountRepository accountRepository;

  @Override
  @Transactional
  public Account create(Account account) {
    return accountRepository.save(account);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "accounts", key = "#id")
  public Account findById(Long id) {
    return accountRepository.findById(id).orElseThrow(
        () -> new AccountNotFoundException(id)
    );
  }


}
