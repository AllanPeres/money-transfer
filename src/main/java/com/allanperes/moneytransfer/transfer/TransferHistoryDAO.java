package com.allanperes.moneytransfer.transfer;

import org.jooq.Configuration;
import org.jooq.example.db.h2.tables.pojos.TransferHistory;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.jooq.example.db.h2.Tables.TRANSFER_HISTORY;

public class TransferHistoryDAO {

    public TransferHistory create(Configuration configuration, Long creditAccountId, Long debitAccountId, LocalDateTime dateTime) {
        return DSL.using(configuration).insertInto(TRANSFER_HISTORY)
                .columns(TRANSFER_HISTORY.CREDIT_ACCOUNT_ID, TRANSFER_HISTORY.DEBIT_ACCOUNT_ID, TRANSFER_HISTORY.DATETIME)
                .values(creditAccountId, debitAccountId, Timestamp.valueOf(dateTime))
                .returning(TRANSFER_HISTORY.ID, TRANSFER_HISTORY.CREDIT_ACCOUNT_ID,
                        TRANSFER_HISTORY.DEBIT_ACCOUNT_ID, TRANSFER_HISTORY.DATETIME)
                .fetchOne()
                .into(TransferHistory.class);
    }

}
