package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.account.*;
import com.allanperes.moneytransfer.infrastructure.MoneyTransferVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class MoneyTransferVerticleTest {

    @BeforeAll
    static void setup(Vertx vertx, VertxTestContext context) {
        MoneyTransferVerticle moneyTransferVerticle = new MoneyTransferVerticle(
                new TransferService(
                        new AccountService(new AccountDAO()),
                        new AccountHistoryService(new AccountHistoryDAO()),
                        new TransferHistoryService(new TransferHistoryDAO())
                ),
                new AccountService(new AccountDAO())
        );
        vertx.deployVerticle(moneyTransferVerticle, context.completing());
    }

    @AfterAll
    static void tearDown(Vertx vertx) {
        vertx.close();
    }

    @ParameterizedTest
    @MethodSource("transferingMoneyArguments")
    @DisplayName("Transfer money from account 3216547 to 124578")
    void transferingMoney(BigDecimal transferedValue, BigDecimal currentValueExpected, Vertx vertx, VertxTestContext context) {
        Transfer transfer = new Transfer("3216547", "124578", transferedValue);
        WebClient webClient = WebClient.create(vertx);
        webClient.post(8080, "localhost", "/api/transfer/transfer")
                .as(BodyCodec.string())
                .sendJson(transfer, httpResponseAsyncResult -> {
                    if (httpResponseAsyncResult.succeeded()) {
                        var result = httpResponseAsyncResult.result();
                        var summarizedAccount = Json.decodeValue(result.body(), SummarizedAccount.class);
                        assertEquals(currentValueExpected, summarizedAccount.getCurrentValue());
                        context.completeNow();
                    } else {
                        context.failNow(context.causeOfFailure());
                    }
                });
    }

    private static Stream<Arguments> transferingMoneyArguments() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(51.25), BigDecimal.valueOf(301.64)),
                Arguments.of(BigDecimal.valueOf(15.20), BigDecimal.valueOf(286.44))
        );
    }

    @Test
    @DisplayName("Should not make the transfer")
    void testNotCompletingTransferWithoutMoney(Vertx vertx, VertxTestContext context) {
        Transfer transfer = new Transfer("3216547", "124578", BigDecimal.valueOf(1000));
        WebClient webClient = WebClient.create(vertx);
        webClient.post(8080, "localhost", "/api/transfer/transfer")
                .as(BodyCodec.string())
                .sendJson(transfer, result -> {
                    if (result.succeeded() && result.result().statusCode() == 500) {
                        assertEquals("There is not enough money on account 3216547", result.result().body());
                        context.completeNow();
                    }
                });
    }

    @Test
    @DisplayName("Should find summarized account")
    void testRecoverAccountInformations(Vertx vertx, VertxTestContext context) {
        WebClient webClient = WebClient.create(vertx);
        webClient.get(8080, "localhost", "/api/account/accountNumber/124578")
                .as(BodyCodec.json(SummarizedAccount.class))
                .send(context.succeeding(response -> {
                    context.verify(() -> {
                        assertEquals(BigDecimal.valueOf(157.02), response.body().getCurrentValue());
                        context.completeNow();
                    });
                }));
    }

    @Test
    @DisplayName("Should not find summarized account")
    void testNotFindAccountInformation(Vertx vertx, VertxTestContext context) {
        WebClient webClient = WebClient.create(vertx);
        webClient.get(8080, "localhost", "/api/account/accountNumber/1245781")
                .as(BodyCodec.string())
                .send(context.succeeding(response -> {
                    context.verify(() -> {
                        assertEquals("This account doens't exists 1245781", response.body());
                        context.completeNow();
                    });
                }));
    }




}
