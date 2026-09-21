package com.atlas.bank.transaction.service;

import com.atlas.bank.account.exception.AccountNotFoundException;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.account.repository.AccountRepository;
import com.atlas.bank.transaction.dto.TransferRequest;
import com.atlas.bank.transaction.exception.AccountNotActiveException;
import com.atlas.bank.transaction.exception.InsufficientFundsException;
import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import com.atlas.bank.transaction.service.fee.FeeCalculator;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransfersService implements ITransferService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final List<FeeCalculator> feeCalculators;

  @Transactional
  @Override
  public Transaction execute(TransferRequest request) {
    Long fromId = request.getSourceAccountId();
    Long toId = request.getTargetAccountId();
    BigDecimal amount = request.getAmount();
    Account from = accountRepository.findById(fromId)
        .orElseThrow(() -> new AccountNotFoundException(fromId)
        );
    Account to = accountRepository.findById(toId)
        .orElseThrow(() -> new AccountNotFoundException(toId)
        );

    // Validar que la cuenta esté activa
    if (!"ACTIVE".equals(from.getStatus())) {
      throw new AccountNotActiveException(fromId, from.getStatus());
    }
    if (!"ACTIVE".equals(to.getStatus())) {
      throw new AccountNotActiveException(toId, to.getStatus());
    }

    // Validar fondos
    if (from.getBalance().compareTo(amount) < 0) {
      throw new InsufficientFundsException(from.getId(), from.getBalance(), amount);
    }

    // Calcular comisión usando los FeeCalculators
    BigDecimal fee = feeCalculators.stream()
        .filter(calculator -> calculator.supports(from.getType()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No FeeCalculator found for account type " + from.getType()))
        .calculate(amount);

    // Actualizar saldos
    from.setBalance(from.getBalance().subtract(amount).subtract(fee));
    to.setBalance(to.getBalance().add(amount));
    accountRepository.save(from);
    accountRepository.save(to);

    // Crear transacción
    Transaction transaction = new Transaction();
    transaction.setType("TRANSFER");
    transaction.setSourceAccountId(fromId);
    transaction.setTargetAccountId(toId);
    transaction.setAmount(amount);
    transaction.setFee(fee);
    transaction.setStatus("EXECUTED");

    return transactionRepository.save(transaction);

  }

}
