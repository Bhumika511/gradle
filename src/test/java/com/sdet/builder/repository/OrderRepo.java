package com.sdet.builder.repository;

import com.sdet.builder.db.DatabaseConfig;
import com.sdet.builder.model.Orders;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Repository Pattern
 *
 * Handles all database operations related to Orders.
 * Test classes never execute SQL directly.
 */
public class OrderRepo {

    private static final Logger log =
            LoggerFactory.getLogger(OrderRepo.class);

    /**
     * Saves an order into the database.
     */
    @Step("Save Order into PostgreSQL")
    public void save(Orders order) {

        log.info("Preparing to save order with SKU: {}", order.sku());

        String sql = """
                INSERT INTO orders
                (sku, qty, price, order_date, shipped)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, order.sku());
            statement.setInt(2, order.qty());
            statement.setDouble(3, order.price());
            statement.setDate(4, Date.valueOf(order.orderDate()));
            statement.setBoolean(5, order.shipped());

            int rows = statement.executeUpdate();

            log.info("Order saved successfully. Rows affected: {}", rows);

        } catch (SQLException e) {

            log.error("Failed to save order.", e);

            throw new RuntimeException(e);
        }
    }

    /**
     * Returns total number of orders.
     */
    @Step("Count Orders from Database")
    public long count() {

        log.info("Counting orders in database.");

        String sql = "SELECT COUNT(*) FROM orders";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()
        ) {

            rs.next();

            long count = rs.getLong(1);

            log.info("Current order count: {}", count);

            return count;

        } catch (SQLException e) {

            log.error("Unable to count orders.", e);

            throw new RuntimeException(e);
        }
    }

    /**
     * Clears the Orders table before each test.
     */
    @Step("Reset Orders Table")
    public void reset() {

        log.info("Resetting orders table.");

        String sql = "TRUNCATE TABLE orders RESTART IDENTITY";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.executeUpdate();

            log.info("Orders table reset successfully.");

        } catch (SQLException e) {

            log.error("Unable to reset orders table.", e);

            throw new RuntimeException(e);
        }
    }
}