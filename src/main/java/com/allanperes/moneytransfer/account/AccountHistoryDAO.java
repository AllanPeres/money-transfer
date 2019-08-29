package com.allanperes.moneytransfer.account;

import com.allanperes.moneytransfer.infrastructure.DatabaseConnection;
import org.jooq.example.db.h2.tables.pojos.AccountHistory;

import java.math.BigDecimal;

import static org.jooq.example.db.h2.tables.Account.ACCOUNT;
import static org.jooq.example.db.h2.tables.AccountHistory.ACCOUNT_HISTORY;
import static org.jooq.impl.DSL.sum;

public class AccountHistoryDAO extends DatabaseConnection {

    BigDecimal getCurrentCurrencyByAccountNumber(String accountNumber) {
        return dsl.select(sum(ACCOUNT_HISTORY.VALUE))
                .from(ACCOUNT_HISTORY
                        .join(ACCOUNT).on(ACCOUNT_HISTORY.ACCOUNT_ID.eq(ACCOUNT.ID)))
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(accountNumber)).fetchOne().value1();
    }

    AccountHistory save(Long accountId, BigDecimal value) {
        return dsl.insertInto(ACCOUNT_HISTORY)
                .columns(ACCOUNT_HISTORY.ACCOUNT_ID, ACCOUNT_HISTORY.VALUE)
                .values(accountId, value)
                .returning(ACCOUNT_HISTORY.ID, ACCOUNT_HISTORY.ACCOUNT_ID, ACCOUNT_HISTORY.VALUE)
                .fetchOne()
                .into(AccountHistory.class);
    }

}
