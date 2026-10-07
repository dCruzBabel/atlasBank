package com.atlas.bank.atlas_bank.domain.event;

import java.time.LocalDateTime;

public record AccountClosedEvent(
    Long accountId,
    String accountNumber,
    String ownerName,
    LocalDateTime closedAt
) {

}