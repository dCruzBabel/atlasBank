package com.atlas_bank.transaction.service.transfer;

import com.atlas_bank.account.model.Account;
import java.math.BigDecimal;

public record TransferContext(Account from, Account to, BigDecimal amount) {
}
