package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.account.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferServiceTest {

    private TransferService transferService = new TransferService(
            new AccountService(new AccountDAO()),
            new AccountHistoryService(new AccountHistoryDAO()),
            new TransferHistoryService(new TransferHistoryDAO()));

    @Test
    @DisplayName("Should transfer money")
    void shouldTransferMoney() {
        String debitAccount = "3216547";
        String creditAccount = "124578";
        BigDecimal transferValue = BigDecimal.valueOf(35.4);
        Transfer transfer = new Transfer(debitAccount, creditAccount, transferValue);
        SummarizedAccount returnedValue = transferService.transfer(transfer);
        assertAll(
                () -> assertEquals(debitAccount, returnedValue.getAccountNumber()),
                () -> assertEquals(returnedValue.getCurrentValue(), BigDecimal.valueOf(251.04))
        );
    }
}
