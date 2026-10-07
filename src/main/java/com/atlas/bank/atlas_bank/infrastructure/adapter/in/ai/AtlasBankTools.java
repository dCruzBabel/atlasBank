package com.atlas.bank.atlas_bank.infrastructure.adapter.in.ai;

import com.atlas.bank.atlas_bank.application.command.TransferMonneyCommand;
import com.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AtlasBankTools {

    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetAccountUseCase getAccountUseCase;

    @Tool(description = "Transferir dinero entre dos cuentas del banco atlas-bank")
    public String transferMoney(
        @ToolParam(description = "ID de la cuenta de origen") String sourceAccountId,
        @ToolParam(description = "ID de la cuenta de destino") String targetAccountId,
        @ToolParam(description = "Monto a transferir") BigDecimal amount
    ){
        try {

            var command = TransferMonneyCommand.builder()
                    .fromId(Long.parseLong(sourceAccountId))
                    .toId(Long.parseLong(targetAccountId))
                    .amount(amount)
                    .build();

            transferMoneyUseCase.transfer(command);
            return "Transferencia realizada con exito para monto $" + amount;


        } catch (InsufficientFundsException e){
            return "Error transferencia por falta de fondos" + e.getMessage();
        } catch (Exception e){
            return "Error transferencia " + e.getMessage();
        }
    }

    @Tool(description = "Consultar el saldo actual de una cuenta del banco")
    public String getAccountBalance(
            @ToolParam(description = "ID de la cuenta a consultar") String accountId
    ){
        try {
            var account = getAccountUseCase.findById(Long.parseLong(accountId));
            return "La cuenta tiene saldo: " + account.getBalance().getAmount();
        } catch (Exception e){
            return "Error consultando cuenta " + e.getMessage();
        }

    }

}
