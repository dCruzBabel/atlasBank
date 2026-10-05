package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.ListAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.out.AccountRepositoryPort;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService implements CreateAccountUseCase, ListAccountUseCase,
    GetAccountUseCase {

  private final AccountRepositoryPort accountRepository;

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
