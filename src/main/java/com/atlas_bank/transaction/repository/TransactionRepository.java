package com.atlas_bank.transaction.repository;

import com.atlas_bank.transaction.model.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);
}