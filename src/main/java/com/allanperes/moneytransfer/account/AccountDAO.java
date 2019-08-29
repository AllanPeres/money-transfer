package com.allanperes.moneytransfer.account;

import com.allanperes.moneytransfer.infrastructure.DatabaseConnection;
import org.jooq.example.db.h2.tables.pojos.Account;

import static org.jooq.example.db.h2.tables.Account.ACCOUNT;

public class AccountDAO extends DatabaseConnection {

    public Account findByAccountNumber(String accountNumber) {
        return dsl.select()
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(accountNumber))
                .fetchOne()
                .into(Account.class);
    }
}
