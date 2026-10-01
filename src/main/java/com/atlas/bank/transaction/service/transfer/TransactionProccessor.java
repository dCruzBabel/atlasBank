package com.atlas.bank.transaction.service.transfer;

import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class TransactionProccessor<C> {

  protected final TransactionRepository transactionRepository;

  @Transactional
  public Transaction process(C context) {
    validate(context);
    BigDecimal fee = calculateFee(context);
    execute(context, fee);
    return save(context, fee);
  }

  protected abstract void validate(C context);

  protected abstract BigDecimal calculateFee(C context);

  protected abstract void execute(C context, BigDecimal fee);

  protected abstract Transaction save(C context, BigDecimal fee);


  protected abstract void validate(TransferContext context);

  protected abstract BigDecimal calculateFee(TransferContext context);

  protected abstract void execute(TransferContext context, BigDecimal fee);

  protected abstract Transaction save(TransferContext context, BigDecimal fee);
}
