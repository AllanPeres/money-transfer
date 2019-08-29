package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.account.AccountHistoryService;
import com.allanperes.moneytransfer.account.AccountService;
import com.allanperes.moneytransfer.account.SummarizedAccount;
import org.jooq.example.db.h2.tables.pojos.Account;

import java.math.BigDecimal;

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

    public SummarizedAccount transfer(Transfer transfer) {
        if (accountHistoryService.hasEnoughValueForTransfer(transfer.getDebitAccountNumber(), transfer.getValue())) {
            this.includeHistories(transfer);
            return accountService.findSummarizedAccountByAccountNumber(transfer.getDebitAccountNumber());
        }
        throw new RuntimeException("There is not enough money on account " + transfer.getDebitAccountNumber());
    }

    private void includeHistories(Transfer transfer) {
        Account debitAccount = this.accountService.findByAccountNumber(transfer.getDebitAccountNumber());
        this.includeDebitHistory(debitAccount.getId(), transfer.getValue());
        Account creditAccount = this.accountService.findByAccountNumber(transfer.getCreditAccountNumber());
        this.includeCreditHistory(creditAccount.getId(), transfer.getValue());
        this.transferHistoryService.save(creditAccount.getId(), debitAccount.getId(), transfer.getDateTime());
    }

    private void includeDebitHistory(Long accountId, BigDecimal debitValue) {
        this.accountHistoryService.save(accountId, debitValue.negate());
    }

    private void includeCreditHistory(Long accountId, BigDecimal creditValue) {
        this.accountHistoryService.save(accountId, creditValue);
    }
}
