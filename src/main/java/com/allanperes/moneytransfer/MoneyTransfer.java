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

import java.util.logging.Logger;

public class MoneyTransfer {

    private static final Logger logger = Logger.getLogger(MoneyTransfer.class.getName());

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
        logger.info("Server intialized at port 8080");
    }
}
