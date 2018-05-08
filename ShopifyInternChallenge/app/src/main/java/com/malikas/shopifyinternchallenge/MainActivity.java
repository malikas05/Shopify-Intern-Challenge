package com.malikas.shopifyinternchallenge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.malikas.shopifyinternchallenge.adapters.ProvinceAdapter;
import com.malikas.shopifyinternchallenge.adapters.SimpleSectionedRecyclerViewAdapter;
import com.malikas.shopifyinternchallenge.adapters.OrderAdapter;
import com.malikas.shopifyinternchallenge.data.ShopifyData;
import com.malikas.shopifyinternchallenge.entity.Order;
import com.malikas.shopifyinternchallenge.entity.Province;
import com.malikas.shopifyinternchallenge.entity.ShippingAddress;
import com.malikas.shopifyinternchallenge.entity.Shopify;
import com.malikas.shopifyinternchallenge.entity.Year;
import com.malikas.shopifyinternchallenge.retrofitAPI.ShopifyEndPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // for retrofit
    private static final String API_BASE_URL = "https://shopicruit.myshopify.com/admin/";
    private Retrofit mRetrofit;
    private ShopifyEndPoint mShopifyEndPoint;
    //

    // UI variables
    @BindView(R.id.recyclerViewProvince)
    RecyclerView recyclerViewProvince;
    @BindView(R.id.recyclerViewYear)
    RecyclerView recyclerViewYear;
    ProgressDialog progressDialog;
    //

    // Adapters for recycler
    private ProvinceAdapter provinceAdapter;
    private OrderAdapter orderAdapter;
    //

    // lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (ShopifyData.getsInstance().getOrders().isEmpty()) {
            prepareRetrofitCall();
            makeCallToShopifyEndPoint();
        }
        else {
            generateRecyclerByProvince();
            generateRecyclerByYears();
        }

        recyclerViewProvince.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProvince.setNestedScrollingEnabled(true);
        recyclerViewYear.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewYear.setNestedScrollingEnabled(true);
    }
    //

    // helper methods
    public void generateRecyclerByProvince(){
        provinceAdapter = new ProvinceAdapter(ShopifyData.getsInstance().getProvinces());
        recyclerViewProvince.setAdapter(provinceAdapter);
    }

    public void generateRecyclerByYears(){
        //This is the code to provide a sectioned list
        //Sections
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
        List<Order> orders = new ArrayList<>();
        int nextSection = 0;
        for (Year year : ShopifyData.getsInstance().getYears()){
            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(nextSection,
                    String.valueOf(year.getOrders().size()) + " of orders created in " +
                            year.getYear()));
            if (!year.getOrders().isEmpty()){
                if (year.getOrders().size() > 10){
                    orders.addAll(year.getOrders().subList(0, 10));
                }
                else {
                    orders.addAll(year.getOrders());
                }
                nextSection = orders.size();
            }
        }
        orderAdapter = new OrderAdapter(orders);

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this,R.layout.recycler_section, R.id.section_text, orderAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        recyclerViewYear.setAdapter(mSectionedAdapter);
    }

    private void prepareRetrofitCall() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(gson)
                )
                .build();

        mShopifyEndPoint = mRetrofit.create(ShopifyEndPoint.class);
    }

    private void makeCallToShopifyEndPoint(){
        showProgressDialog();
        Call<Shopify> call = mShopifyEndPoint.getOrders();
        call.enqueue(new Callback<Shopify>() {
            @Override
            public void onResponse(Call<Shopify> call, Response<Shopify> response) {
                ArrayList<Order> orders = response.body().getOrders();
                if (orders != null) {
                    ShopifyData.getsInstance().setOrders(orders);
                    countOrdersByProvinces(orders);
                    countOrdersByYear(orders);

                    generateRecyclerByProvince();
                    generateRecyclerByYears();
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<Shopify> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Unfortunately something went wrong. Please try later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void countOrdersByProvinces(ArrayList<Order> orders){
        Map<String, Province> mapProvincies = new TreeMap<>();
        for (Order order : orders){
            ShippingAddress shippingAddress = order.getShippingAddress();
            if (shippingAddress != null) {
                if (mapProvincies.containsKey(shippingAddress.getProvince())) {
                    mapProvincies.get(shippingAddress.getProvince()).getOrders().add(order);
                } else {
                    List<Order> orderList = new ArrayList<>();
                    orderList.add(order);
                    mapProvincies.put(shippingAddress.getProvince(),
                            new Province(shippingAddress.getProvince(), orderList));
                }
            }
        }

        for (Province province : mapProvincies.values()){
            ShopifyData.getsInstance().getProvinces().add(province);
        }
    }

    private void countOrdersByYear(ArrayList<Order> orders){
        Map<String, Year> mapYears = new TreeMap<>(Collections.<String>reverseOrder());
        for (Order order : orders){
            String year = formatDate("yyyy", order.getCreatedAt());
            if (mapYears.containsKey(year)){
                mapYears.get(year).getOrders().add(order);
            }
            else {
                List<Order> orderList = new ArrayList<>();
                orderList.add(order);
                mapYears.put(year, new Year(year, orderList));
            }
        }

        for (Year year : mapYears.values()){
            ShopifyData.getsInstance().getYears().add(year);
        }
    }

    private String formatDate(String format, Date date){
        DateFormat df = new DateFormat();
        String formattedDate = df.format(format, date).toString();
        return formattedDate;
    }

    //show/hide progress dialog
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    //

    // Listeners
    @OnClick(R.id.btnSeeMoreProvince)
    public void btnSeeMoreProvinceClicked(){
        Intent intent = new Intent(this, ProvinceActivity.class);
        startActivity(intent);
    }
    //
}
