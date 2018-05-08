package com.malikas.shopifyinternchallenge.retrofitAPI;

import com.malikas.shopifyinternchallenge.entity.Order;
import com.malikas.shopifyinternchallenge.entity.Shopify;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Malik on 2018-05-07.
 */

public interface ShopifyEndPoint {

    @GET("orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6")
    Call<Shopify> getOrders();
}
