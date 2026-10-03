package com.atlas_bank.account.service;

import com.atlas_bank.account.dto.DashboardResponse;
import com.atlas_bank.account.model.Account;
import com.atlas_bank.transaction.dto.TransactionMapper;
import com.atlas_bank.transaction.dto.TransactionResponse;
import com.atlas_bank.transaction.service.ITransactionQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {
  private final IAccountService accountService;
  private final ITransactionQueryService transactionQueryService;
  private final TransactionMapper transactionMapper;

  public DashboardResponse getDashboard(Long accountId) {
    Account account = accountService.findById(accountId);
    List<TransactionResponse> transactions = transactionQueryService
        .getByAccountId(accountId)
        .stream()
        .map(transactionMapper::toResponse)
        .toList();

    return DashboardResponse.builder()
        .accountId(account.getId())
        .accountNumber(account.getAccountNumber())
        .ownerName(account.getOwnerName())
        .type(account.getType().name())
        .balance(account.getBalance().getAmount())
        .status(account.getStatus().name())
        .recentTransactions(transactions)
        .build();
  }
}
