package com.allanperes.moneytransfer.account;

import org.jooq.Configuration;
import org.jooq.example.db.h2.tables.pojos.AccountHistory;
import org.jooq.impl.DSL;

import java.math.BigDecimal;

import static com.allanperes.moneytransfer.infrastructure.DatabaseConnection.createConnection;
import static org.jooq.example.db.h2.tables.Account.ACCOUNT;
import static org.jooq.example.db.h2.tables.AccountHistory.ACCOUNT_HISTORY;
import static org.jooq.impl.DSL.sum;

public class AccountHistoryDAO {

    BigDecimal getCurrentCurrencyByAccountNumber(String accountNumber) {
        return createConnection().select(sum(ACCOUNT_HISTORY.VALUE))
                .from(ACCOUNT_HISTORY
                        .join(ACCOUNT).on(ACCOUNT_HISTORY.ACCOUNT_ID.eq(ACCOUNT.ID)))
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(accountNumber)).fetchOne().value1();
    }

    AccountHistory save(Configuration configuration, Long accountId, BigDecimal value) {
        return DSL.using(configuration).insertInto(ACCOUNT_HISTORY)
                .columns(ACCOUNT_HISTORY.ACCOUNT_ID, ACCOUNT_HISTORY.VALUE)
                .values(accountId, value)
                .returning(ACCOUNT_HISTORY.ID, ACCOUNT_HISTORY.ACCOUNT_ID, ACCOUNT_HISTORY.VALUE)
                .fetchOne()
                .into(AccountHistory.class);
    }

}
