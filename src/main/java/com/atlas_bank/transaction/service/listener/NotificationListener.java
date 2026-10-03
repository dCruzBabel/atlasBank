package com.atlas_bank.transaction.service.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

  @EventListener
  public void handleTransactionExecutedEvent(Object event) {
    log.info("Received event: {}", event);
  }
}
