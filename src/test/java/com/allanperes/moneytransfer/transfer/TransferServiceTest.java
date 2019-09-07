package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.account.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

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
                () -> assertEquals(BigDecimal.valueOf(26.04), returnedValue.getCurrentValue())
        );
    }

    @Test
    @DisplayName("Should not transfer money")
    void shouldNotTransferMoney() {
        String debitAccount = "3216547";
        String creditAccount = "124578";
        BigDecimal transferValue = BigDecimal.valueOf(1000);
        Transfer transfer = new Transfer(debitAccount, creditAccount, transferValue);
        assertThrows(RuntimeException.class, () -> transferService.transfer(transfer));
    }

    @Test
    @DisplayName("Reliable concurrency transfers")
    void shouldValidateCorrectlyConcurrencyTransfers() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Callable<Void> firstTransfer = () -> {
            String debitAccount = "3216547";
            String creditAccount = "124578";
            BigDecimal transferValue = BigDecimal.valueOf(100);
            Transfer transfer = new Transfer(debitAccount, creditAccount, transferValue);
            SummarizedAccount returnedValue = transferService.transfer(transfer);
            assertAll(
                    () -> assertEquals(debitAccount, returnedValue.getAccountNumber()),
                    () -> assertEquals(BigDecimal.valueOf(151.04), returnedValue.getCurrentValue())
            );
            return null;
        };

        Callable<Void> seccondTranfer = () -> {
            String debitAccount = "3216547";
            String creditAccount = "124578";
            BigDecimal transferValue = BigDecimal.valueOf(100);
            Transfer transfer = new Transfer(debitAccount, creditAccount, transferValue);
            SummarizedAccount returnedValue = transferService.transfer(transfer);
            assertAll(
                    () -> assertEquals(debitAccount, returnedValue.getAccountNumber()),
                    () -> assertEquals(BigDecimal.valueOf(51.04), returnedValue.getCurrentValue())
            );
            return null;
        };

        Callable<Void> errorTransfer = () -> {
            String debitAccount = "3216547";
            String creditAccount = "124578";
            BigDecimal transferValue = BigDecimal.valueOf(125);
            Transfer transfer = new Transfer(debitAccount, creditAccount, transferValue);
            assertThrows(RuntimeException.class, () -> transferService.transfer(transfer));
            return null;
        };

        executorService.invokeAll(List.of(firstTransfer, seccondTranfer, errorTransfer));
    }
}
