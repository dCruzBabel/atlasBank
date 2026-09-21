package com.atlas.bank.transaction.dto;

import com.atlas.bank.transaction.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  Transaction toEntity(TransferRequest request);

  TransactionResponse toResponse(Transaction transaction);
}
