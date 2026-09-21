package com.atlas.bank.account.service;

import com.atlas.bank.account.model.Account;
import com.atlas.bank.account.repository.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

  private final AccountRepository accountRepository;

  @Override
  public Account create(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  @Override
  public Account findById(Long id) {
    return accountRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Account not found with id: " + id)
    );
  }


}
