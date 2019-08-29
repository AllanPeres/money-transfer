package com.allanperes.moneytransfer.account;

import org.jooq.example.db.h2.tables.pojos.Account;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account findByAccountNumber(String accountNumber) {
        return accountDAO.findByAccountNumber(accountNumber);
    }
}
