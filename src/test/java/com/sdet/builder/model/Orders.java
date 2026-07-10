package com.sdet.builder.model;
import java.time.LocalDate;

public record Orders(
        String sku,
        int qty,
        double price,
        LocalDate orderDate,
        boolean shipped

) {
    }
