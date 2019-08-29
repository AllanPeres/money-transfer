package com.allanperes.moneytransfer.account;

import java.math.BigDecimal;

public class AccountHistoryService {

    private AccountHistoryDAO accountHistoryDAO;

    public AccountHistoryService(AccountHistoryDAO accountHistoryDAO) {
        this.accountHistoryDAO = accountHistoryDAO;
    }

    public boolean hasEnoughValueForTransfer(String accountNumber, BigDecimal transferValue) {
        BigDecimal currentCurrency = accountHistoryDAO.getCurrentCurrencyByAccountNumber(accountNumber);
        return currentCurrency.compareTo(transferValue) >= 0;
    }
}
