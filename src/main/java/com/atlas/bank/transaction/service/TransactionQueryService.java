package com.atlas.bank.transaction.service;

import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements ITransactionQueryService {
  private final TransactionRepository transactionRepository;


  @Override
  public List<Transaction> getByAccountId(Long accountId) {
    return transactionRepository
        .findBySourceAccountIdOrTargetAccountId(accountId, accountId);
  }

}
