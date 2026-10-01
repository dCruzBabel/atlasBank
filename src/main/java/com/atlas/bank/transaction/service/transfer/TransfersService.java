package com.atlas.bank.transaction.service.transfer;

import com.atlas.bank.account.exception.AccountNotFoundException;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.account.repository.AccountRepository;
import com.atlas.bank.transaction.dto.TransferRequest;
import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import com.atlas.bank.transaction.service.event.TransactionExecutedEvent;
import com.atlas.bank.transaction.service.factory.TransactionFactory;
import com.atlas.bank.transaction.service.fee.FeeCalculator;
import com.atlas.bank.transaction.validation.chain.TransferValidator;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TransfersService extends TransactionProccessor<TransferContext> implements ITransferService {

  private final AccountRepository accountRepository;
  private final List<FeeCalculator> feeCalculators;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final List<TransferValidator> validators;


  public TransfersService(TransactionRepository transactionRepository,
      AccountRepository accountRepository, List<FeeCalculator> feeCalculators,
      ApplicationEventPublisher applicationEventPublisher, List<TransferValidator> validators) {
    super(transactionRepository);
    this.accountRepository = accountRepository;
    this.feeCalculators = feeCalculators;
    this.applicationEventPublisher = applicationEventPublisher;
    this.validators = validators;

  }

  @Transactional
  @Override
  public Transaction execute(TransferRequest request) {
    //Buscar cuentas
    Long fromId = request.getSourceAccountId();
    Long toId = request.getTargetAccountId();
    BigDecimal amount = request.getAmount();
    Account from = accountRepository.findById(fromId)
        .orElseThrow(() -> new AccountNotFoundException(fromId)
        );
    Account to = accountRepository.findById(toId)
        .orElseThrow(() -> new AccountNotFoundException(toId)
        );

    Transaction transaction = process(new TransferContext(from, to, amount));
    applicationEventPublisher.publishEvent(new TransactionExecutedEvent(
        transaction.getId(),
        transaction.getType(),
        transaction.getSourceAccountId(),
        transaction.getTargetAccountId(),
        transaction.getAmount(),
        transaction.getFee()
    ));
    return transaction;
  }

  @Override
  protected void validate(TransferContext context) {

    for (TransferValidator validator : validators) {
      validator.validate(context);
    }

  }

  @Override
  protected BigDecimal calculateFee(TransferContext context) {
    // Calcular comisión usando los FeeCalculators
    return feeCalculators.stream()
        .filter(calculator -> calculator.supports(context.from().getType()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No FeeCalculator found for account type "
            + context.from().getType()))
        .calculate(context.amount());
  }

  @Override
  protected void execute(TransferContext context, BigDecimal fee) {
    // Actualizar saldos
    context.from().setBalance(context.from().getBalance().subtract(context.amount()).subtract(fee));
    context.to().setBalance(context.to().getBalance().add(context.amount()));
    accountRepository.save(context.from());
    accountRepository.save(context.to());
  }

  @Override
  protected Transaction save(TransferContext context, BigDecimal fee) {
    // Crear transacción
    Transaction transaction =
        TransactionFactory.createTransfer(context, fee);

    return transactionRepository.save(transaction);
  }
}
