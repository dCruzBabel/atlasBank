package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AccountResponse {

  private Long id;
  private String accountNumber;
  private String ownerName;
  private String email;
  private String type; //SAVING. CHECKING
  private BigDecimal balance; //ACTIVE,CLOSED, FROZEN
  private String status;
  private LocalDateTime createdAt;

}
