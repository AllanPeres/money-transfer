package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.account.*;
import org.jooq.example.db.h2.tables.AccountHistory;
import org.jooq.example.db.h2.tables.pojos.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TransferServiceTest {

    private TransferService transferService = new TransferService(
            new AccountService(new AccountDAO()),
            new AccountHistoryService(new AccountHistoryDAO()));

    @Test
    @DisplayName("Should transfer money")
    void shouldTransferMoney() {
        String debtAccount = "3216547";
        String creditAccount = "124578";
        BigDecimal transferValue = BigDecimal.valueOf(35.4);
        Transfer transfer = new Transfer(debtAccount, creditAccount, transferValue);
        SummarizedAccount returnedValue = transferService.doTransfer(transfer);
        assertNull(returnedValue);
    }
}
