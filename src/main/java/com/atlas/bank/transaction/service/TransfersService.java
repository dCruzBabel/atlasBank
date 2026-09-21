package com.atlas.bank.transaction.service;

import com.atlas.bank.account.model.Account;
import com.atlas.bank.account.repository.AccountRepository;
import com.atlas.bank.transaction.dto.TransferRequest;
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
        .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada: " + fromId)
        );
    Account to = accountRepository.findById(toId)
        .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada: " + toId)
        );

    // Validar que la cuenta esté activa
    if (!"ACTIVE".equals(from.getStatus())) {
      throw new RuntimeException("La cuenta origen no está activa");
    }
    if (!"ACTIVE".equals(to.getStatus())) {
      throw new RuntimeException("La cuenta destino no está activa");
    }

    // Validar fondos
    if (from.getBalance().compareTo(amount) < 0) {
      throw new RuntimeException("Fondos insuficientes");
    }

    // Calcular comisión usando los FeeCalculators
    BigDecimal fee = feeCalculators.stream()
        .filter(calculator -> calculator.supports(from.getType()))
        .findFirst()
        .map(calculator -> calculator.calculate(amount))
        .orElse(BigDecimal.ZERO);

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
