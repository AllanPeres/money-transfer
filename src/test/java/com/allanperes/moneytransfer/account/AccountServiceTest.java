package com.allanperes.moneytransfer.account;

import org.jooq.example.db.h2.tables.pojos.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("Finding accounts by account number")
    void findAccountsByAccountNumber1() {
        String accountNumber = "1245781";
        assertThrows(
                RuntimeException.class,
                () -> accountService.findByAccountNumber(accountNumber),
                "This account doens't exists " + accountNumber);
    }
}
