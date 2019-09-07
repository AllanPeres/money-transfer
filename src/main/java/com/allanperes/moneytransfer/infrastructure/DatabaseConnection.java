package com.allanperes.moneytransfer.infrastructure;

import org.jooq.DSLContext;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;
import static org.jooq.impl.DSL.using;

public class DatabaseConnection {

    private static final DatabaseProperties databaseProperties;

    static {
        databaseProperties = DatabaseProperties.getInstance();
    }

    public static DSLContext createConnection() {
        try {
            Connection connection = getConnection(databaseProperties.getUrl(),
                    databaseProperties.getUsername(),
                    databaseProperties.getPassword());
            return using(connection);

        } catch (SQLException exeption) {
            throw new RuntimeException("Cannot connect to database, cause " + exeption.getMessage());
        }
    }
}
