package com.malikas.shopifyinternchallenge.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malikas.shopifyinternchallenge.R;
import com.malikas.shopifyinternchallenge.entity.Item;
import com.malikas.shopifyinternchallenge.entity.Order;

import java.util.Date;
import java.util.List;


/**
 * Created by Malik on 2018-05-07.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    private List<Order> orders;

    public class OrderHolder extends RecyclerView.ViewHolder{
        private TextView orderId;
        private TextView orderDate;
        private TextView items;
        private TextView shippingAddress;

        public OrderHolder(View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.orderId);
            orderDate = itemView.findViewById(R.id.orderDate);
            items = itemView.findViewById(R.id.items);
            shippingAddress = itemView.findViewById(R.id.shippingAddress);
        }

        public void bindYear(int position){
            Order order = orders.get(position);
            orderId.setText("Order ID: " + order.getId());

            orderDate.setText("Date: " + formatDate("MM/dd/yyyy", order.getCreatedAt()));

            if (order.getShippingAddress() != null) {
                shippingAddress.setText("Shipping address: " + order.getShippingAddress().getAddress() +
                ", " + order.getShippingAddress().getCity() + ", " + order.getShippingAddress().getProvince());
            }
            else {
                shippingAddress.setText("Shipping address: not found");
            }

            String itemsString = "";
            for (Item item : order.getItems()){
                itemsString += item.getName() + " - " + item.getQuantity() + "\n";
            }
            items.setText("Items:\n" + itemsString);
        }
    }

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_order, parent, false);

        return new OrderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        holder.bindYear(position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private String formatDate(String format, Date date){
        DateFormat df = new DateFormat();
        String formattedDate = df.format(format, date).toString();
        return formattedDate;
    }
}
