package com.allanperes.moneytransfer.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountServiceTest {

    private AccountService accountService = new AccountService(new AccountDAO(), new AccountHistoryDAO());

    @ParameterizedTest
    @MethodSource("enoughMoneyValues")
    @DisplayName("Verify if there is enought money on account")
    void testVerifyEnoughMoney(BigDecimal transferValue, String accountNumber, Boolean response) {
        assertEquals(accountService.hasMoreThanTransferValue(accountNumber, transferValue), response);
    }

    static Stream<Arguments> enoughMoneyValues() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(10.5), "124578", Boolean.TRUE),
                Arguments.of(BigDecimal.valueOf(100.57), "124578", Boolean.TRUE),
                Arguments.of(BigDecimal.valueOf(100.58), "124578", Boolean.FALSE),
                Arguments.of(BigDecimal.valueOf(10.5), "3216547", Boolean.TRUE)
        );
    }
}
