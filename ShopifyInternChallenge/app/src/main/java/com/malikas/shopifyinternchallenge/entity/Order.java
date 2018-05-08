package com.malikas.shopifyinternchallenge.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Malik on 2018-05-07.
 */

public class Order {

    @SerializedName("id")
    private String id;
    @SerializedName("created_at")
    private Timestamp createdAt;
    @SerializedName("shipping_address")
    private ShippingAddress shippingAddress;
    @SerializedName("line_items")
    private List<Item> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
