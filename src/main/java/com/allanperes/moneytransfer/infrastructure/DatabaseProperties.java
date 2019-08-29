package com.allanperes.moneytransfer.infrastructure;

import com.allanperes.moneytransfer.MoneyTransfer;

import java.io.IOException;
import java.util.Properties;

import static java.util.Objects.isNull;

class DatabaseProperties {

    private static DatabaseProperties instance;
    private String url;
    private String username;
    private String password;

    private DatabaseProperties() {
        try {
            Properties properties = new Properties();
            properties.load(MoneyTransfer.class.getResourceAsStream("/config.properties"));
            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");
        } catch (IOException exeption) {
            throw new RuntimeException("Cannot read config.properties");
        }
    }

    static DatabaseProperties getInstance() {
        if (isNull(instance)) {
            instance = new DatabaseProperties();
        }
        return instance;
    }

    String getUrl() {
        return url;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}
