package com.atlas_bank.account.dto;

import com.atlas_bank.account.model.Account;
import com.atlas_bank.shared.model.Currency;
import com.atlas_bank.shared.model.Email;
import com.atlas_bank.shared.model.Money;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "balance", source = "balance", qualifiedByName = "toMoney")
  @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
  Account toEntity(CreateAccountRequest request);

  @Mapping(target = "balance", source = "balance", qualifiedByName = "toAmount")
  @Mapping(target = "email", source = "email", qualifiedByName = "fromEmail")
  AccountResponse toResponse(Account account);

  @Named("toMoney")
  default Money toMoney(BigDecimal amount) {
    if (amount == null) {
      return null;
    }
    return Money.of(amount, Currency.USD);
  }

  @Named("fromEmail")
  default String fromEmail(Email email) {
    if (email == null) {
      return null;
    }
    return email.getAddress();
  }

  @Named("toEmail")
  default com.atlas_bank.shared.model.Email toEmail(String email) {
    if (email == null) {
      return null;
    }
    return Email.of(email);
  }

  @Named("toAmount")
  default BigDecimal toAmount(Money money) {
    if (money == null) {
      return null;
    }
    return money.getAmount();
  }

}
