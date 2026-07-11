package com.sdet.builder.test;

import com.sdet.builder.builder.OrderBuilder;
import com.sdet.builder.factory.OrderFactory;
import com.sdet.builder.model.Orders;
import com.sdet.builder.repository.OrderRepo;
import io.qameta.allure.*;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Order Automation Framework")
@Feature("Order Management")
@Owner("Bhumika")
public class OrderTest {

    private static final Logger log =
            LoggerFactory.getLogger(OrderTest.class);

    private static final OrderRepo repo = new OrderRepo();
    private static final OrderFactory factory = new OrderFactory(repo);

    @BeforeAll
    static void migrateDatabase() {

        log.info("Starting Flyway migration...");

        Flyway flyway = Flyway.configure()
                .dataSource(
                        System.getProperty("db.url",
                                "jdbc:postgresql://localhost:5432/testdb"),
                        System.getProperty("db.user",
                                "postgres"),
                        System.getProperty("db.password",
                                "postgres")
                )
                .load();

        flyway.migrate();

        log.info("Flyway migration completed successfully.");
    }

    @BeforeEach
    void setup() {

        log.info("Resetting database before test execution.");

        repo.reset();
    }

    @Test
    @Story("Create Order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a new order is created successfully.")

    void createsOrder() {

        log.info("===== Test Started : createsOrder =====");

        Orders order = createOrder();

        persistOrder(order);

        verifyOrderCount(1);

        Allure.addAttachment(
                "Expected Order Count",
                "1"
        );

        log.info("===== Test Passed =====");
    }

    @Test
    @Story("Count Orders")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify total number of orders.")

    void countsOrders() {

        log.info("===== Test Started : countsOrders =====");

        Orders order = createDefaultOrder();

        persistOrder(order);

        verifyOrderCount(1);

        log.info("===== Test Passed =====");
    }

    @Step("Create Order with Quantity = 3")
    private Orders createOrder() {

        return OrderBuilder.anOrder()
                .withQty(3)
                .build();
    }

    @Step("Create Default Order")
    private Orders createDefaultOrder() {

        return OrderBuilder.anOrder()
                .build();
    }

    @Step("Persist Order")
    private void persistOrder(Orders order) {

        factory.persisted(order);
    }

    @Step("Verify Order Count is {expected}")
    private void verifyOrderCount(long expected) {

        long actual = repo.count();

        Allure.addAttachment(
                "Actual Order Count",
                String.valueOf(actual)
        );

        log.info("Expected Count : {}", expected);
        log.info("Actual Count : {}", actual);

        assertEquals(expected, actual);
    }

}