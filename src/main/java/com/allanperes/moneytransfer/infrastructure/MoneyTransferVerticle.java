package com.allanperes.moneytransfer.infrastructure;

import com.allanperes.moneytransfer.account.AccountService;
import com.allanperes.moneytransfer.transfer.Transfer;
import com.allanperes.moneytransfer.transfer.TransferService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class MoneyTransferVerticle extends AbstractVerticle {

    private static final String ACCOUNT_API = "/api/account";
    private static final String TRANSFER_API = "/api/transfer";

    private TransferService transferService;
    private AccountService accountService;

    public MoneyTransferVerticle(TransferService transferService, AccountService accountService) {
        this.transferService = transferService;
        this.accountService = accountService;
    }

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.get(ACCOUNT_API + "/accountNumber/:accountNumber")
                .produces("application/json")
                .handler(getAccountByAccountNumberHandler());

        router.route().handler(BodyHandler.create());

        router.post(TRANSFER_API + "/transfer")
                .produces("application/json")
                .handler(postTransferBetweenAccountsHandler());

        router.route("/api/*").failureHandler(failureHandler());

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }

    private Handler<RoutingContext> postTransferBetweenAccountsHandler() {
        return routingContext -> {
            var bufferBody = routingContext.getBody();
            var transfer = Json.decodeValue(bufferBody, Transfer.class);
            var summarizedAccount = this.transferService.transfer(transfer);
            var response = routingContext.response();
            response.putHeader("content-type", "application/json")
                    .setChunked(true)
                    .write(Json.encode(summarizedAccount))
                    .end();
        };
    }

    private Handler<RoutingContext> getAccountByAccountNumberHandler() {
        return routingContext -> {
            var accountNumber = routingContext.request().getParam("accountNumber");
            var summarizedAccount = this.accountService.findSummarizedAccountByAccountNumber(accountNumber);
            var response = routingContext.response();
            response.putHeader("content-type", "application/json")
                    .setChunked(true)
                    .write(Json.encode(summarizedAccount))
                    .end();
        };
    }

    private Handler<RoutingContext> failureHandler() {
        return routingContext -> {
            routingContext.response()
                    .setStatusCode(500)
                    .setChunked(true)
                    .write(routingContext.failure().getMessage())
                    .end();
        };
    }
}
