package com.allanperes.moneytransfer.account;

import java.math.BigDecimal;

public class AccountService {

    private AccountDAO accountDAO;
    private AccountHistoryDAO accountHistoryDAO;

    public AccountService(AccountDAO accountDAO, AccountHistoryDAO accountHistoryDAO) {
        this.accountDAO = accountDAO;
        this.accountHistoryDAO = accountHistoryDAO;
    }

    public boolean hasMoreThanTransferValue(String accountNumber, BigDecimal transferValue) {
        BigDecimal currentCurrency = accountHistoryDAO.getCurrentCurrencyByAccountNumber(accountNumber);
        return currentCurrency.compareTo(transferValue) >= 0;
    }
}
