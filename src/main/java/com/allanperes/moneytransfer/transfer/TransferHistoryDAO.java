package com.allanperes.moneytransfer.transfer;

import com.allanperes.moneytransfer.infrastructure.DatabaseConnection;
import org.jooq.example.db.h2.tables.pojos.TransferHistory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.example.db.h2.Tables.ACCOUNT;
import static org.jooq.example.db.h2.Tables.TRANSFER_HISTORY;

public class TransferHistoryDAO extends DatabaseConnection {

    public List<TransferHistory> findAllByDebitAccountNumber(String debitAccountNumber) {
        return dsl.select()
                .from(TRANSFER_HISTORY.join(ACCOUNT)
                    .on(TRANSFER_HISTORY.DEBIT_ACCOUNT_ID.eq(ACCOUNT.ID)))
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(debitAccountNumber))
                .fetch().into(TransferHistory.class);
    }

    public List<TransferHistory> findAllByCreditAccountNumber(String creditAccountNumber) {
        return dsl.select()
                .from(TRANSFER_HISTORY.join(ACCOUNT)
                        .on(TRANSFER_HISTORY.CREDIT_ACCOUNT_ID.eq(ACCOUNT.ID)))
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(creditAccountNumber))
                .fetch().into(TransferHistory.class);
    }

    public TransferHistory create(Long creditAccountId, Long debitAccountId, LocalDateTime dateTime) {
        return dsl.insertInto(TRANSFER_HISTORY)
                .columns(TRANSFER_HISTORY.CREDIT_ACCOUNT_ID, TRANSFER_HISTORY.DEBIT_ACCOUNT_ID, TRANSFER_HISTORY.DATETIME)
                .values(creditAccountId, debitAccountId, Timestamp.valueOf(dateTime))
                .returning(TRANSFER_HISTORY.ID, TRANSFER_HISTORY.CREDIT_ACCOUNT_ID,
                        TRANSFER_HISTORY.DEBIT_ACCOUNT_ID, TRANSFER_HISTORY.DATETIME)
                .fetchOne()
                .into(TransferHistory.class);
    }

}
