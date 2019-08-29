package com.allanperes.moneytransfer.infrastructure;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class MoneyTransferVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        Route messageRoute = router.get("/api/helloworld");
        messageRoute.handler(routingContext -> {
//            routingContext.response().end(accountDAO.getAcconts().toString());
        });

        router.get().handler(StaticHandler.create());

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }
}
