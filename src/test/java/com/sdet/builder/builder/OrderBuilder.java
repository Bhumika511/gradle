package com.sdet.builder.builder;


import com.sdet.builder.model.Orders;

import java.time.LocalDate;

public class OrderBuilder {

    private String sku = "SKU-1";
    private int qty = 1;
    private double price = 1299.00;
    private LocalDate orderDate = LocalDate.now();
    private boolean shipped = false;

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public OrderBuilder withQty(int qty) {
        this.qty = qty;
        return this;
    }

    public OrderBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public OrderBuilder withShipped(boolean shipped) {
        this.shipped = shipped;
        return this;
    }

    public Orders build() {
        return new Orders(
                sku,
                qty,
                price,
                orderDate,
                shipped
        );
    }
    }
