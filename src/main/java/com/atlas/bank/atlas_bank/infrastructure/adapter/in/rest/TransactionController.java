package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import com.atlas.bank.atlas_bank.application.command.TransferMonneyCommand;
import com.atlas.bank.atlas_bank.application.port.in.GetTransactionsByAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.atlas_bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.atlas_bank.application.query.TransactionReadModel;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransferRequest;
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

  private final TransferMoneyUseCase transferMoneyUsecase;
  private final GetTransactionsByAccountUseCase getTransactionsByAccountUseCase;
  private final TransactionMapper transactionMapper;

  @PostMapping("/transfer")
  public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {

    TransferMonneyCommand command = TransferMonneyCommand.builder()
        .fromId(request.getSourceAccountId())
        .toId(request.getTargetAccountId())
        .amount(request.getAmount())
        .build();

    Transaction transaction = transferMoneyUsecase.transfer(command);

    return ResponseEntity.ok(transactionMapper.toResponse(transaction));

  }

  @GetMapping("/{id}/transactions")
  public ResponseEntity<List<TransactionReadModel>> getTransactions(@PathVariable Long id) {

    GetAccountStatementQuery query = new GetAccountStatementQuery(id);
    List<TransactionReadModel> response = getTransactionsByAccountUseCase.getByAccountId(query);

    return ResponseEntity.ok(response);
  }

}
