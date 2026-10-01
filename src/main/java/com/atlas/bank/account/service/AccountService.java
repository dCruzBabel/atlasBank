package com.atlas.bank.account.service;

import com.atlas.bank.account.exception.AccountNotFoundException;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.account.repository.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

  private final AccountRepository accountRepository;

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
  public Account findById(Long id) {
    return accountRepository.findById(id).orElseThrow(
        () -> new AccountNotFoundException(id)
    );
  }


}
