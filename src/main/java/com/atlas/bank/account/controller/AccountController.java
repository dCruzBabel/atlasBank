package com.atlas.bank.account.controller;

import com.atlas.bank.account.dto.AccountMapper;
import com.atlas.bank.account.dto.AccountResponse;
import com.atlas.bank.account.dto.CreateAccountRequest;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.account.service.IAccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final IAccountService accountService;
  private final AccountMapper accountMapper;

  @PostMapping
  public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest request) {
    Account savedAccount = accountService.create(accountMapper.toEntity(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(savedAccount));
  }

  @GetMapping
  public ResponseEntity<List<AccountResponse>> findAll() {
    return ResponseEntity.ok(accountService.findAll().stream().map(accountMapper::toResponse).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(accountMapper.toResponse(accountService.findById(id)));
  }

}
