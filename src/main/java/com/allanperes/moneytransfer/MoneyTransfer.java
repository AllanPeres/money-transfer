package com.allanperes.moneytransfer;

import com.allanperes.moneytransfer.infrastructure.MoneyTransferVerticle;
import io.vertx.core.Vertx;

public class MoneyTransfer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MoneyTransferVerticle());
    }
}
