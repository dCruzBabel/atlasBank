package com.atlas.bank.transaction.service;

import com.atlas.bank.transaction.dto.TransferRequest;
import com.atlas.bank.transaction.model.Transaction;

public interface ITransferService {

  Transaction execute(TransferRequest request);


}
