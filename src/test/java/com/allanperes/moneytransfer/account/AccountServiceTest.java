package com.allanperes.moneytransfer.account;

import org.jooq.example.db.h2.tables.pojos.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTest {

    private AccountService accountService = new AccountService(new AccountDAO());

    @Test
    @DisplayName("Finding accounts by account number")
    void findAccountsByAccountNumber() {
        String accountNumber = "124578";
        Account accountDesired = new Account(1L, accountNumber);
        Account accountFinded = accountService.findByAccountNumber(accountNumber);
        assertAll(
                () -> assertEquals(accountDesired.getId(), accountFinded.getId()),
                () -> assertEquals(accountDesired.getAccountNumber(), accountFinded.getAccountNumber())
        );
    }
}
