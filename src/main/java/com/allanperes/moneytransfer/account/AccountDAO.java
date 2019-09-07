package com.allanperes.moneytransfer.account;

import org.jooq.example.db.h2.tables.pojos.Account;

import static com.allanperes.moneytransfer.infrastructure.DatabaseConnection.createConnection;
import static org.jooq.example.db.h2.Tables.ACCOUNT_HISTORY;
import static org.jooq.example.db.h2.tables.Account.ACCOUNT;
import static org.jooq.impl.DSL.sum;

public class AccountDAO {

    Account findByAccountNumber(String accountNumber) {
        return createConnection().select()
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(accountNumber))
                .fetchOne()
                .into(Account.class);
    }

    SummarizedAccount findSummarizedAccountByAccountNumber(String accountNumber) {
        return createConnection().select(ACCOUNT.ID, ACCOUNT.ACCOUNT_NUMBER, sum(ACCOUNT_HISTORY.VALUE).as("currentValue"))
                .from(ACCOUNT.join(ACCOUNT_HISTORY).on(ACCOUNT.ID.eq(ACCOUNT_HISTORY.ACCOUNT_ID)))
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(accountNumber))
                .groupBy(ACCOUNT.ID, ACCOUNT.ACCOUNT_NUMBER)
                .fetchOptional()
                .orElseThrow(() -> new RuntimeException("This account doens't exists " + accountNumber))
                .into(SummarizedAccount.class);
    }
}
