package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.port.in.GetTransactionsByAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.out.TransactionRepositoryPort;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements GetTransactionsByAccountUseCase {
  private final TransactionRepositoryPort transactionRepository;


  @Override
  public List<Transaction> getByAccountId(Long accountId) {
    return transactionRepository
        .findBySourceAccountIdOrTargetAccountId(accountId, accountId);
  }

}
