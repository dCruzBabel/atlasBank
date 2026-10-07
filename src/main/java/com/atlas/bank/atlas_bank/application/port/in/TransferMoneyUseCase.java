package com.atlas.bank.atlas_bank.application.port.in;

import com.atlas.bank.atlas_bank.application.command.TransferMonneyCommand;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;

public interface TransferMoneyUseCase {
  Transaction transfer(TransferMonneyCommand command);
}