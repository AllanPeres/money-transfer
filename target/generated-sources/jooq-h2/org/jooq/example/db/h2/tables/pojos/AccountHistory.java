/*
 * This file is generated by jOOQ.
 */
package org.jooq.example.db.h2.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountHistory implements Serializable {

    private static final long serialVersionUID = 2086080826;

    private Long       id;
    private Long       accountId;
    private BigDecimal value;

    public AccountHistory() {}

    public AccountHistory(AccountHistory value) {
        this.id = value.id;
        this.accountId = value.accountId;
        this.value = value.value;
    }

    public AccountHistory(
        Long       id,
        Long       accountId,
        BigDecimal value
    ) {
        this.id = id;
        this.accountId = accountId;
        this.value = value;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AccountHistory (");

        sb.append(id);
        sb.append(", ").append(accountId);
        sb.append(", ").append(value);

        sb.append(")");
        return sb.toString();
    }
}
