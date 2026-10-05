package com.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.atlas_bank.application.port.out.TransactionRepositoryPort;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaTransactionRepositoryAdapter implements TransactionRepositoryPort {

  private final SpringDataTransactionRepository jpaRepository;
  private final TransactionPersistenceMapper mapper;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public Transaction save(Transaction transaction) {
    transaction.initDefaults();
    TransactionJpaEntity entity = mapper.toJpaEntity(transaction);
    TransactionJpaEntity savedEntity = jpaRepository.save(entity);

    transaction.getDomainEvents().forEach(eventPublisher::publishEvent);
    transaction.clearDomainEvents();

    return mapper.toDomain(savedEntity);
  }

  @Override
  public List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId) {
    return jpaRepository.findBySourceAccountIdOrTargetAccountId(sourceId, targetId).stream()
        .map(mapper::toDomain)
        .toList();
  }
}
