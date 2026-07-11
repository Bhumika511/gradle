package com.sdet.builder.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConfig is responsible for creating
 * database connections used by the Repository layer.
 */
public class DatabaseConfig {

    // Logger for recording database activities
    private static final Logger log =
            LoggerFactory.getLogger(DatabaseConfig.class);

    // Prevent object creation
    private DatabaseConfig() {
    }

    /**
     * Creates and returns a PostgreSQL database connection.
     */
    public static Connection getConnection() throws SQLException {

        log.info("Reading database configuration...");

        String url = System.getProperty(
                "db.url",
                "jdbc:postgresql://localhost:5432/testdb"
        );

        String user = System.getProperty(
                "db.user",
                "postgres"
        );

        String password = System.getProperty(
                "db.password",
                "postgres"
        );

        log.info("Connecting to PostgreSQL database...");
        log.debug("Database URL : {}", url);

        try {

            Connection connection =
                    DriverManager.getConnection(url, user, password);

            log.info("Database connection established successfully.");

            return connection;

        } catch (SQLException e) {

            log.error("Unable to connect to the database.", e);

            throw e;
        }
    }
}