package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.account.AccountHistoryService;
import com.allanperes.moneytransfer.account.AccountService;
import com.allanperes.moneytransfer.account.SummarizedAccount;
import com.allanperes.moneytransfer.infrastructure.DatabaseConnection;
import org.jooq.Configuration;
import org.jooq.example.db.h2.tables.pojos.Account;

import java.math.BigDecimal;

import static com.allanperes.moneytransfer.infrastructure.DatabaseConnection.createConnection;

public class TransferService {

    private AccountService accountService;
    private AccountHistoryService accountHistoryService;
    private TransferHistoryService transferHistoryService;

    public TransferService(AccountService accountService,
                           AccountHistoryService accountHistoryService,
                           TransferHistoryService transferHistoryService) {
        this.accountService = accountService;
        this.accountHistoryService = accountHistoryService;
        this.transferHistoryService = transferHistoryService;
    }

    public synchronized SummarizedAccount transfer(Transfer transfer) {
        if (accountHistoryService.hasEnoughValueForTransfer(transfer.getDebitAccountNumber(), transfer.getValue())) {
            this.includeHistories(transfer);
            return accountService.findSummarizedAccountByAccountNumber(transfer.getDebitAccountNumber());
        }
        throw new RuntimeException("There is not enough money on account " + transfer.getDebitAccountNumber());
    }

    private void includeHistories(Transfer transfer) {
        createConnection().transaction(configuration -> {
            Account debitAccount = this.accountService.findByAccountNumber(transfer.getDebitAccountNumber());
            this.includeDebitHistory(configuration, debitAccount.getId(), transfer.getValue());
            Account creditAccount = this.accountService.findByAccountNumber(transfer.getCreditAccountNumber());
            this.includeCreditHistory(configuration, creditAccount.getId(), transfer.getValue());
            this.transferHistoryService.save(configuration, creditAccount.getId(), debitAccount.getId(), transfer.getDateTime());
        });
    }

    private void includeDebitHistory(Configuration configuration, Long accountId, BigDecimal debitValue) {
        this.accountHistoryService.save(configuration, accountId, debitValue.negate());
    }

    private void includeCreditHistory(Configuration configuration,Long accountId, BigDecimal creditValue) {
        this.accountHistoryService.save(configuration, accountId, creditValue);
    }
}
