package com.allanperes.moneytransfer.account;

import org.jooq.example.db.h2.tables.pojos.Account;

import java.util.Optional;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(accountDAO.findByAccountNumber(accountNumber))
                .orElseThrow(() -> new RuntimeException("This account doens't exists " + accountNumber));
    }
}
