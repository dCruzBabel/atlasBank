package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import com.atlas.bank.atlas_bank.application.command.CloseAccountCommand;
import com.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import com.atlas.bank.atlas_bank.application.facade.AccountDashboardFacade;
import com.atlas.bank.atlas_bank.application.port.in.CloseAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.ListAccountUseCase;
import com.atlas.bank.atlas_bank.application.query.DashBoardReadModel;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountMapper;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

  private final CreateAccountUseCase createAccountUseCase;
  private final ListAccountUseCase listAccountUseCase;
  private final GetAccountUseCase getAccountUseCase;
  private final CloseAccountUseCase closeAccountUseCase;
  private final AccountMapper accountMapper;
  private final AccountDashboardFacade accountDashboardFacade;

  @GetMapping("/{id}/dashboard")
  public ResponseEntity<DashBoardReadModel> getDashboard(@PathVariable Long id) {
    return ResponseEntity.ok(accountDashboardFacade.getDashboard(id));
  }

  @PostMapping
  public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
    CreateAccountCommand command = CreateAccountCommand.builder()
        .accountNumber(request.getAccountNumber())
        .ownerName(request.getOwnerName())
        .email(request.getEmail())
        .type(request.getType())
        .balance(request.getBalance())
        .build();
    Account savedAccount = createAccountUseCase.create(command);
    return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(savedAccount));
  }

  @GetMapping
  public ResponseEntity<List<AccountResponse>> findAll() {
    return ResponseEntity.ok(listAccountUseCase.findAll().stream().map(accountMapper::toResponse).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(accountMapper.toResponse(getAccountUseCase.findById(id)));
  }

  @PatchMapping("/{id}/close")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<AccountResponse> close(@PathVariable Long id) {
    CloseAccountCommand command = CloseAccountCommand.builder().accountId(id).build();
    Account closedAccount = closeAccountUseCase.close(command);
    return ResponseEntity.ok(accountMapper.toResponse(closedAccount));
  }

}
