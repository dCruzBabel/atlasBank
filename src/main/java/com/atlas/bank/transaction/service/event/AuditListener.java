package com.atlas.bank.transaction.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditListener {

  @EventListener
  public void onTransactionExecutedEvent(TransactionExecutedEvent event) {
    log.info("Audit listener received event: {}", event);
  }

}
