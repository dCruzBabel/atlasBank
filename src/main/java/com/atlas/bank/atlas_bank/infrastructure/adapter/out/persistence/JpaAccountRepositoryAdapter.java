package com.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.atlas_bank.application.port.out.AccountRepositoryPort;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {

  private final SpringDataAccountRepository accountRepository;
  private final AccountPersistenceMapper mapper;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public Optional<Account> findById(Long id) {
    return accountRepository.findById(id)
        .map(mapper::toDomain);
  }

  @Override
  public List<Account> findAll() {
    return accountRepository.findAll()
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public Account save(Account account) {
    account.initDefaults();
    AccountJpaEntity jpaEntity = mapper.toJpaEntity(account);
    AccountJpaEntity savedEntity = accountRepository.save(jpaEntity);

    account.getDomainEvents().forEach(eventPublisher::publishEvent);
    account.clearDomainEvents();

    return mapper.toDomain(savedEntity);
  }
}
