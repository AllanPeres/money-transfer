package com.allanperes.moneytransfer.account;

import java.math.BigDecimal;

public class SummarizedAccount {

    private Long id;
    private BigDecimal currentValue;

    public SummarizedAccount() {
    }

    public SummarizedAccount(Long id, BigDecimal currentValue) {
        this.id = id;
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
}
