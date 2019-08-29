package com.allanperes.moneytransfer.transfer;

import org.jooq.example.db.h2.tables.pojos.TransferHistory;

import java.time.LocalDateTime;

public class TransferHistoryService {

    private TransferHistoryDAO transferHistoryDAO;

    public TransferHistoryService(TransferHistoryDAO transferHistoryDAO) {
        this.transferHistoryDAO = transferHistoryDAO;
    }


    public TransferHistory save(Long creditAccountDesired, Long debitAccountDesired, LocalDateTime dateTime) {
        return transferHistoryDAO.create(creditAccountDesired, debitAccountDesired, dateTime);
    }
}
