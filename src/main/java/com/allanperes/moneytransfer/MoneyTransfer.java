package com.allanperes.moneytransfer;

import com.allanperes.moneytransfer.account.AccountDAO;
import com.allanperes.moneytransfer.account.AccountHistoryDAO;
import com.allanperes.moneytransfer.account.AccountHistoryService;
import com.allanperes.moneytransfer.account.AccountService;
import com.allanperes.moneytransfer.infrastructure.MoneyTransferVerticle;
import com.allanperes.moneytransfer.transfer.TransferHistoryDAO;
import com.allanperes.moneytransfer.transfer.TransferHistoryService;
import com.allanperes.moneytransfer.transfer.TransferService;
import io.vertx.core.Vertx;

public class MoneyTransfer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        AccountService accountService = new AccountService(new AccountDAO());
        vertx.deployVerticle(
                new MoneyTransferVerticle(
                        new TransferService(
                                accountService,
                                new AccountHistoryService(new AccountHistoryDAO()),
                                new TransferHistoryService(new TransferHistoryDAO())),
                        accountService));
    }
}
