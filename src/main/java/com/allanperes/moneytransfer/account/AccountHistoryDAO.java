package com.allanperes.moneytransfer.account;

import com.allanperes.moneytransfer.infrastructure.DatabaseConnection;

import java.math.BigDecimal;

import static org.jooq.example.db.h2.tables.Account.ACCOUNT;
import static org.jooq.example.db.h2.tables.AccountHistory.ACCOUNT_HISTORY;
import static org.jooq.impl.DSL.sum;

public class AccountHistoryDAO extends DatabaseConnection {

    public BigDecimal getCurrentCurrencyByAccountNumber(String accountNumber) {
        return dsl.select(sum(ACCOUNT_HISTORY.VALUE))
                .from(ACCOUNT_HISTORY
                        .join(ACCOUNT).on(ACCOUNT_HISTORY.ACCOUNT_ID.eq(ACCOUNT.ID)))
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(accountNumber)).fetchOne().value1();
    }

}
