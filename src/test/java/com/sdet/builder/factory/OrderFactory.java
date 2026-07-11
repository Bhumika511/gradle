package com.sdet.builder.factory;

import com.sdet.builder.model.Orders;
import com.sdet.builder.repository.OrderRepo;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory Pattern
 *
 * Responsible for persisting test data into the database.
 * Tests interact with the Factory instead of directly calling the Repository.
 */
public class OrderFactory {

    private static final Logger log =
            LoggerFactory.getLogger(OrderFactory.class);

    private final OrderRepo repository;

    public OrderFactory(OrderRepo repository) {

        this.repository = repository;
    }

    /**
     * Saves the Order into the database
     */
    @Step("Persist Order into Database")
    public Orders persisted(Orders order) {

        log.info("Persisting Order with SKU : {}", order.sku());

        repository.save(order);

        log.info("Order persisted successfully.");

        return order;
    }

}