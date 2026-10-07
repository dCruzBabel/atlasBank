package com.atlas.bank.atlas_bank.application.command;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record CreateAccountCommand(
    String accountNumber,
    String ownerName,
    String email,
    AccountType type,
    BigDecimal balance
) {
}
