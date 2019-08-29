package com.allanperes.moneytransfer.account;

import java.math.BigDecimal;

public class SummarizedAccount {

    private Long id;
    private String accountNumber;
    private BigDecimal currentValue;

    public SummarizedAccount() {
    }

    public SummarizedAccount(Long id, String accountNumber, BigDecimal currentValue) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.currentValue = currentValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
