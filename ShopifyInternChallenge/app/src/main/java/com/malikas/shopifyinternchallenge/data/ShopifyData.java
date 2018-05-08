package com.malikas.shopifyinternchallenge.data;

import com.malikas.shopifyinternchallenge.entity.Order;
import com.malikas.shopifyinternchallenge.entity.Province;
import com.malikas.shopifyinternchallenge.entity.Year;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malik on 2018-05-07.
 */

public class ShopifyData {

    private static ShopifyData sInstance;

    private List<Province> provinces;
    private List<Year> years;
    private ArrayList<Order> orders;

    private ShopifyData() {
        provinces = new ArrayList<>();
        years = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public static ShopifyData getsInstance(){
        if (sInstance == null)
            sInstance = new ShopifyData();
        return sInstance;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
