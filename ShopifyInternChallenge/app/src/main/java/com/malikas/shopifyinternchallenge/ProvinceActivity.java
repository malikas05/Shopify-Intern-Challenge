package com.malikas.shopifyinternchallenge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.malikas.shopifyinternchallenge.adapters.OrderAdapter;
import com.malikas.shopifyinternchallenge.adapters.ProvinceAdapter;
import com.malikas.shopifyinternchallenge.adapters.SimpleSectionedRecyclerViewAdapter;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProvinceActivity extends AppCompatActivity {

    // UI variables
    @BindView(R.id.recyclerViewProvince)
    RecyclerView recyclerViewProvince;
    //

    // Adapter for recycler
    private OrderAdapter orderAdapter;
    //

    // lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        ButterKnife.bind(this);

        recyclerViewProvince.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProvince.setNestedScrollingEnabled(true);
        generateRecyclerByProvinces();
    }
    //

    // helper methods
    public void generateRecyclerByProvinces(){
        //Sections
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
        List<Order> orders = new ArrayList<>();
        int nextSection = 0;
        for (Province province : ShopifyData.getsInstance().getProvinces()){
            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(nextSection,
                            province.getProvinceName() + " - " + province.getOrders().size()));
            if (!province.getOrders().isEmpty()){
                orders.addAll(province.getOrders());
                nextSection = orders.size();
            }
        }
        orderAdapter = new OrderAdapter(orders);

        //Adding adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this, R.layout.recycler_section, R.id.section_text, orderAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Applying this adapter to the RecyclerView
        recyclerViewProvince.setAdapter(mSectionedAdapter);
    }
    //

}
