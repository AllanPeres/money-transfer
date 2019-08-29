package com.allanperes.moneytransfer.transfer;

import org.jooq.example.db.h2.tables.pojos.TransferHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferHistoryServiceTest {

    private TransferHistoryService transferHistoryService = new TransferHistoryService(new TransferHistoryDAO());

    @Test
    @DisplayName("Should create a history")
    void testCreateHistory() {
        Long historyExpected = 1L;
        Long creditAccountDesired = 1L;
        Long debitAccountDesired = 2L;
        LocalDateTime dateTimeDesired = LocalDateTime.of(2019, 8, 29, 11,17);
        TransferHistory returnedTranferHistory = transferHistoryService.save(
                creditAccountDesired, debitAccountDesired, dateTimeDesired);
        assertAll(
                () -> assertEquals(historyExpected, returnedTranferHistory.getId()),
                () -> assertEquals(creditAccountDesired, returnedTranferHistory.getCreditAccountId()),
                () -> assertEquals(debitAccountDesired, returnedTranferHistory.getDebitAccountId()),
                () -> assertEquals(Timestamp.valueOf(dateTimeDesired), returnedTranferHistory.getDatetime())
        );
    }
}
