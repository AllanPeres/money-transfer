package com.allanperes.moneytransfer.infrastructure;

import com.allanperes.moneytransfer.MoneyTransfer;
import org.jooq.DSLContext;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;
import static org.jooq.impl.DSL.using;

public class DatabaseConnection {

    protected static Connection connection;
    protected static DSLContext dsl;

    public DatabaseConnection() {
        try {
            Properties properties = new Properties();
            properties.load(MoneyTransfer.class.getResourceAsStream("/config.properties"));
            connection = getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));
            dsl = using(connection);
        } catch (IOException | SQLException exeption) {
            throw new RuntimeException("Cannot read config.properties");
        }
    }
}
