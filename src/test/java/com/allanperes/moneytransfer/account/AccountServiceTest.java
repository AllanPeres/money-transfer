package com.allanperes.moneytransfer.account;

import org.jooq.example.db.h2.tables.pojos.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
    @DisplayName("Return error when used wrong account number")
    void dontFindAccountByWrongAccountNumber() {
        String accountNumber = "1245781";
        assertThrows(
                RuntimeException.class,
                () -> accountService.findByAccountNumber(accountNumber),
                "This account doens't exists " + accountNumber);
    }

    @Test
    @DisplayName("Finding summarized accounts by account number")
    void findSummarizedAccountsByAccountNumber() {
        String accountNumber = "124578";
        SummarizedAccount summarizedAccountDesired = new SummarizedAccount(1L, accountNumber, BigDecimal.valueOf(90.57));
        SummarizedAccount summarizedAccountFinded = accountService.findSummarizedAccountByAccountNumber(accountNumber);
        assertAll(
                () -> assertEquals(summarizedAccountDesired.getAccountNumber(), summarizedAccountFinded.getAccountNumber()),
                () -> assertEquals(summarizedAccountDesired.getCurrentValue(), summarizedAccountFinded.getCurrentValue())
        );
    }

    @Test
    @DisplayName("Don't find summarized accounts by wrong account number")
    void dontFindSummarizedAccountByWrongNumber() {
        String accountNumber = "1245781";
        assertThrows(
                RuntimeException.class,
                () -> accountService.findSummarizedAccountByAccountNumber(accountNumber),
                "This account doens't exists " + accountNumber);
    }
}
