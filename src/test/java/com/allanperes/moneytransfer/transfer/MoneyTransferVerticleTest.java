package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.infrastructure.MoneyTransferVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class MoneyTransferVerticleTest {

    @BeforeAll
    static void setup(Vertx vertx, VertxTestContext context) {
        vertx.deployVerticle(new MoneyTransferVerticle(), context.completing());
    }

    @AfterAll
    static void tearDown(Vertx vertx) {
        vertx.close();
    }

    @Test
    @DisplayName("Transfer money from account 3216547 to 124578")
    void transferingMoney(Vertx vertx, VertxTestContext context) {
        Transfer transfer = new Transfer("3216547", "124578", BigDecimal.valueOf(322.25));
        WebClient webClient = WebClient.create(vertx);
        webClient.post("/api/transfer")
                .as(BodyCodec.string())
                .sendJson(transfer, response -> {
//                    if (response.succeeded()) {
//                        HttpResponse<String> result = response.result();
//                        assertEquals("teste", result.body());
//                        context.completeNow();
//                    } else {
//                        context.failNow(context.causeOfFailure());
//                    }
                    context.completeNow();
                });
    }
}
