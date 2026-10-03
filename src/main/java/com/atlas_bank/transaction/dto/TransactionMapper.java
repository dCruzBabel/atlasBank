package com.atlas_bank.transaction.dto;

import com.atlas_bank.transaction.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  Transaction toEntity(TransferRequest request);

  TransactionResponse toResponse(Transaction transaction);
}
