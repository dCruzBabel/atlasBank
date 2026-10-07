package com.atlas.bank.atlas_bank.application.command;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record TransferMonneyCommand(
    Long fromId,
    Long toId,
    BigDecimal amount
) {
}
