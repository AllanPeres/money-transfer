package com.allanperes.moneytransfer.infrastructure;

import org.jooq.DSLContext;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;
import static org.jooq.impl.DSL.using;

public class DatabaseConnection {

    protected static DSLContext dsl;

    public DatabaseConnection() {
        try {
            DatabaseProperties databaseProperties = DatabaseProperties.getInstance();
            Connection connection = getConnection(databaseProperties.getUrl(),
                    databaseProperties.getUsername(),
                    databaseProperties.getPassword());
            dsl = using(connection);
        } catch (SQLException exeption) {
            throw new RuntimeException("Cannot connect to database, cause " + exeption.getMessage());
        }
    }
}
