package com.example.demo.util;

import org.springframework.data.domain.Sort;

import java.util.List;

public class PageHelper {
    public static String orderBy(Sort sort) {
        if (sort.isEmpty()) {
            return "id DESC";
        }

        List<Sort.Order> orders = sort.toList();
        List<String> orderbys = orders.stream().map(order -> order.getProperty() + " " + order.getDirection()).toList();
        return String.join(", ", orderbys);
    }
}
