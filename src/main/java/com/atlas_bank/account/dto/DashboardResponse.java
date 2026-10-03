package com.atlas_bank.account.dto;

import com.atlas_bank.transaction.dto.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
  private long accountId;
  private String accountNumber;
  private String ownerName;
  private String type;
  private BigDecimal balance;
  private String status;
  private List<TransactionResponse> recentTransactions;
}
