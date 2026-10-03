package com.atlas_bank.transaction.service.fraud;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExternalFraudCheckAdapter implements FraudChecker {

  @Override
  public FraudCheckResult check(Long accountId, BigDecimal amount) {

    ExternalFraudResponse response = callExternalApi(accountId, amount);
    log.info("External fraud check response: {}", response);

    if (response.getRecommendation().equals("BLOCK")) {
      return FraudCheckResult.blocked("Transaction blocked due to high fraud risk");
    } else if (response.getRecommendation().equals("ALLOW")) {
      return FraudCheckResult.allowed();
    }

    return FraudCheckResult.allowed();
  }

  private ExternalFraudResponse callExternalApi(Long accountId, BigDecimal amount) {

    if (amount.compareTo(new BigDecimal("10000")) > 0) {
      return new ExternalFraudResponse("HIGH", 0.95, "BLOCK");
    }

    return new ExternalFraudResponse("LOW", 0.1, "ALLOW");
  }

}
