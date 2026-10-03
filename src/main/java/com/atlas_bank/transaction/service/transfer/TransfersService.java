package com.atlas_bank.transaction.service.transfer;

import com.atlas_bank.account.exception.AccountNotFoundException;
import com.atlas_bank.account.model.Account;
import com.atlas_bank.account.repository.DomainAccountRepository;
import com.atlas_bank.transaction.dto.TransferRequest;
import com.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.transaction.repository.TransactionRepository;
import com.atlas_bank.transaction.service.domain.TransferDomainService;
import com.atlas_bank.transaction.service.factory.TransactionFactory;
import com.atlas_bank.transaction.service.fee.FeeCalculator;
import com.atlas_bank.transaction.validation.chain.TransferValidator;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransfersService extends TransactionProccessor<TransferContext> implements ITransferService {

  private final DomainAccountRepository accountRepository;
  private final List<FeeCalculator> feeCalculators;
  private final List<TransferValidator> validators;
  private final TransferDomainService transferDomainService;


  public TransfersService(TransactionRepository transactionRepository,
      DomainAccountRepository accountRepository, List<FeeCalculator> feeCalculators,
      List<TransferValidator> validators,
      TransferDomainService transferDomainService) {
    super(transactionRepository);
    this.accountRepository = accountRepository;
    this.feeCalculators = feeCalculators;
    this.validators = validators;
    this.transferDomainService = transferDomainService;

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

    transaction.executeTransfer();
    transactionRepository.save(transaction);

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
    transferDomainService.transfer(context.from(), context.to(), context.amount(), fee);
  }

  @Override
  protected Transaction save(TransferContext context, BigDecimal fee) {
    // Crear transacción
    Transaction transaction =
        TransactionFactory.createTransfer(context, fee);

    return transactionRepository.save(transaction);
  }
}
