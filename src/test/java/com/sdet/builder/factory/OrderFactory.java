package com.sdet.builder.factory;

import com.sdet.builder.builder.OrderBuilder;
import com.sdet.builder.model.Orders;
import com.sdet.builder.repository.OrderRepo;

public class OrderFactory {

    private final OrderRepo repository;

    public OrderFactory(OrderRepo repo) {
        this.repository = repo;
    }

    public Orders persisted(Orders order) {
        repository.save(order);
        return order;
    }

    }
