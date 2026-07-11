package com.sdet.builder.builder;

import com.sdet.builder.model.Orders;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Builder Pattern
 *
 * Creates reusable test data with sensible default values.
 * Individual fields can be overridden depending on the test scenario.
 */
public class OrderBuilder {

    private static final Logger log =
            LoggerFactory.getLogger(OrderBuilder.class);

    // Default test data
    private String sku = "SKU-1";
    private int qty = 1;
    private double price = 1299.00;
    private LocalDate orderDate = LocalDate.now();
    private boolean shipped = false;

    // Static factory method
    @Step("Create new Order Builder")
    public static OrderBuilder anOrder() {

        log.info("Creating Order Builder");

        return new OrderBuilder();
    }

    @Step("Setting SKU as {sku}")
    public OrderBuilder withSku(String sku) {

        log.info("Updating SKU : {}", sku);

        this.sku = sku;

        return this;
    }

    @Step("Setting Quantity as {qty}")
    public OrderBuilder withQty(int qty) {

        log.info("Updating Quantity : {}", qty);

        this.qty = qty;

        return this;
    }

    @Step("Setting Price as {price}")
    public OrderBuilder withPrice(double price) {

        log.info("Updating Price : {}", price);

        this.price = price;

        return this;
    }

    @Step("Setting Shipped Status as {shipped}")
    public OrderBuilder withShipped(boolean shipped) {

        log.info("Updating Shipping Status : {}", shipped);

        this.shipped = shipped;

        return this;
    }

    @Step("Building Order Object")
    public Orders build() {

        log.info("Building Order Object");

        return new Orders(
                sku,
                qty,
                price,
                orderDate,
                shipped
        );
    }
}