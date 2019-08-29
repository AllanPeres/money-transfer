package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.account.AccountHistoryService;
import com.allanperes.moneytransfer.account.AccountService;
import com.allanperes.moneytransfer.account.SummarizedAccount;

public class TransferService {

    private AccountService accountService;
    private AccountHistoryService accountHistoryService;

    public TransferService(AccountService accountService, AccountHistoryService accountHistoryService) {
        this.accountService = accountService;
        this.accountHistoryService = accountHistoryService;
    }

    public SummarizedAccount doTransfer(Transfer transfer) {

        return null;
    }
}
