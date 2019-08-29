package com.allanperes.moneytransfer.transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {

    private String debitAccountNumber;
    private String creditAccountNumber;
    private BigDecimal value;
    private LocalDateTime dateTime;

    public Transfer() {
        this.dateTime = LocalDateTime.now();
    }

    public Transfer(String debitAccountNumber, String creditAccountNumber, BigDecimal value) {
        this();
        this.debitAccountNumber = debitAccountNumber;
        this.creditAccountNumber = creditAccountNumber;
        this.value = value;
    }

    public String getDebitAccountNumber() {
        return debitAccountNumber;
    }

    public void setDebitAccountNumber(String debitAccountNumber) {
        this.debitAccountNumber = debitAccountNumber;
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
