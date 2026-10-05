package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateAccountRequest {

  @NotBlank(message = "Account number is required")
  private String accountNumber;

  @NotBlank(message = "Owner name is required")
  private String ownerName;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @NotNull(message = "Account type is required")
  private AccountType type;

  @PositiveOrZero(message = "Balance must be zero or positive")
  private BigDecimal balance;

}
