package com.allanperes.moneytransfer.transfer;

import org.jooq.Configuration;
import org.jooq.example.db.h2.tables.pojos.TransferHistory;

import java.time.LocalDateTime;

public class TransferHistoryService {

    private TransferHistoryDAO transferHistoryDAO;

    public TransferHistoryService(TransferHistoryDAO transferHistoryDAO) {
        this.transferHistoryDAO = transferHistoryDAO;
    }


    public TransferHistory save(Configuration configuration, Long creditAccountDesired, Long debitAccountDesired, LocalDateTime dateTime) {
        return transferHistoryDAO.create(configuration, creditAccountDesired, debitAccountDesired, dateTime);
    }
}
