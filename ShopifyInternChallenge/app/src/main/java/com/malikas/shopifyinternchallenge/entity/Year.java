package com.malikas.shopifyinternchallenge.entity;

import java.util.List;

/**
 * Created by Malik on 2018-05-07.
 */

public class Year {

    private String year;
    private List<Order> orders;

    public Year(String year, List<Order> orders) {
        this.year = year;
        this.orders = orders;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
