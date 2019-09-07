package com.allanperes.moneytransfer.transfer;

import org.jooq.DSLContext;
import org.jooq.example.db.h2.tables.pojos.TransferHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.allanperes.moneytransfer.infrastructure.DatabaseConnection.createConnection;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferHistoryServiceTest {

    private TransferHistoryService transferHistoryService = new TransferHistoryService(new TransferHistoryDAO());

    @Test
    @DisplayName("Should create a history")
    void testCreateHistory() {
        Long creditAccountDesired = 1L;
        Long debitAccountDesired = 2L;
        LocalDateTime dateTimeDesired = LocalDateTime.of(2019, 8, 29, 11,17);
        DSLContext context = createConnection();
        TransferHistory returnedTranferHistory = transferHistoryService.save(context.configuration(),
                creditAccountDesired, debitAccountDesired, dateTimeDesired);
        assertAll(
                () -> assertEquals(creditAccountDesired, returnedTranferHistory.getCreditAccountId()),
                () -> assertEquals(debitAccountDesired, returnedTranferHistory.getDebitAccountId()),
                () -> assertEquals(Timestamp.valueOf(dateTimeDesired), returnedTranferHistory.getDatetime())
        );
    }
}
