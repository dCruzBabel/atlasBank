package com.atlas_bank.transaction.controller;

import com.atlas_bank.transaction.dto.TransactionMapper;
import com.atlas_bank.transaction.dto.TransactionResponse;
import com.atlas_bank.transaction.dto.TransferRequest;
import com.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas_bank.transaction.service.transfer.ITransferService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final ITransferService transferService;
  private final ITransactionQueryService transactionQueryService;
  private final TransactionMapper transactionMapper;

  @PostMapping("/transfer")
  public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
    var transaction = transferService.execute(request);
    return ResponseEntity.ok(transactionMapper.toResponse(transaction));

  }

  @GetMapping("/{id}/transactions")
  public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long id) {
    List<Transaction> transactions = transactionQueryService.getByAccountId(id);
    List<TransactionResponse> response = transactions.stream().map(transactionMapper::toResponse)
        .toList();
    return ResponseEntity.ok(response);
  }

}
