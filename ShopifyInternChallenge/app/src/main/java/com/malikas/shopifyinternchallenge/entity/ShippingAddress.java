package com.malikas.shopifyinternchallenge.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Malik on 2018-05-07.
 */

public class ShippingAddress {

    private String name;
    @SerializedName("address1")
    private String address;
    private String phone;
    private String city;
    private String province;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
