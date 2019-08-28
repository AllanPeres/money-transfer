package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.infrastructure.DatabaseConnection;
import org.jooq.example.db.h2.tables.pojos.TransferHistory;

import java.util.List;

import static org.jooq.example.db.h2.Tables.ACCOUNT;
import static org.jooq.example.db.h2.Tables.TRANSFER_HISTORY;

public class TransferHistoryDAO extends DatabaseConnection {

    public List<TransferHistory> findAllByDebtAccountNumber(String debtAccountNumber) {
        return dsl.select()
                .from(TRANSFER_HISTORY.join(ACCOUNT)
                    .on(TRANSFER_HISTORY.DEBT_ACCOUNT_ID.eq(ACCOUNT.ID)))
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(debtAccountNumber))
                .fetch().into(TransferHistory.class);
    }

    public List<TransferHistory> findAllByCreditAccountNumber(String creditAccountNumber) {
        return dsl.select()
                .from(TRANSFER_HISTORY.join(ACCOUNT)
                        .on(TRANSFER_HISTORY.CREDIT_ACCOUNT_ID.eq(ACCOUNT.ID)))
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(creditAccountNumber))
                .fetch().into(TransferHistory.class);
    }

    public void create(TransferHistory transferHistory) {
        dsl.insertInto(TRANSFER_HISTORY)
                .columns(TRANSFER_HISTORY.CREDIT_ACCOUNT_ID, TRANSFER_HISTORY.DEBT_ACCOUNT_ID, TRANSFER_HISTORY.DATETIME)
                .values(transferHistory.getCreditAccountId(), transferHistory.getDebtAccountId(),
                        transferHistory.getDatetime());
    }

}
