package com.allanperes.moneytransfer.account;

import org.jooq.example.db.h2.tables.pojos.AccountHistory;

import java.math.BigDecimal;

public class AccountHistoryService {

    private AccountHistoryDAO accountHistoryDAO;

    public AccountHistoryService(AccountHistoryDAO accountHistoryDAO) {
        this.accountHistoryDAO = accountHistoryDAO;
    }

    public BigDecimal findCurrentAmountByAccountNumber(String accountNumber) {
        return accountHistoryDAO.getCurrentCurrencyByAccountNumber(accountNumber);
    }

    public boolean hasEnoughValueForTransfer(String accountNumber, BigDecimal transferValue) {
        BigDecimal currentCurrency = accountHistoryDAO.getCurrentCurrencyByAccountNumber(accountNumber);
        return currentCurrency.compareTo(transferValue) >= 0;
    }

    public AccountHistory includeHistory(Long accountId, BigDecimal value) {
        return accountHistoryDAO.include(accountId, value);
    }
}
