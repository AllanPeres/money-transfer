package com.allanperes.moneytransfer.transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {

    private String debtAccountNumber;
    private String creditAccountNumber;
    private BigDecimal value;
    private LocalDateTime dateTime;

    public Transfer() {
    }

    public Transfer(String debtAccountNumber, String creditAccountNumber, BigDecimal value) {
        this.debtAccountNumber = debtAccountNumber;
        this.creditAccountNumber = creditAccountNumber;
        this.value = value;
        this.dateTime = LocalDateTime.now();
    }

    public String getDebtAccountNumber() {
        return debtAccountNumber;
    }

    public void setDebtAccountNumber(String debtAccountNumber) {
        this.debtAccountNumber = debtAccountNumber;
    }

    public String getCreditAccountNumber() {
        return creditAccountNumber;
    }

    public void setCreditAccountNumber(String creditAccountNumber) {
        this.creditAccountNumber = creditAccountNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
