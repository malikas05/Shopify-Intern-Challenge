package com.malikas.shopifyinternchallenge.entity;

import java.util.List;

/**
 * Created by Malik on 2018-05-07.
 */

public class Province {

    private String provinceName;
    private List<Order> orders;

    public Province(String provinceName, List<Order> orders) {
        this.provinceName = provinceName;
        this.orders = orders;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
