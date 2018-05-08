package com.malikas.shopifyinternchallenge.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malikas.shopifyinternchallenge.R;
import com.malikas.shopifyinternchallenge.entity.Province;

import java.util.List;


/**
 * Created by Malik on 2018-05-07.
 */

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceHolder> {

    private List<Province> provincies;

    public class ProvinceHolder extends RecyclerView.ViewHolder{
        private TextView title;

        public ProvinceHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }

        public void bindProvince(int position){
            title.setText(provincies.get(position).getOrders().size() + " of orders from " +
                    provincies.get(position).getProvinceName());
        }
    }

    public ProvinceAdapter(List<Province> provincies) {
        this.provincies = provincies;
    }

    @Override
    public ProvinceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_province, parent, false);

        return new ProvinceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProvinceHolder holder, int position) {
        holder.bindProvince(position);
    }

    @Override
    public int getItemCount() {
        return provincies.size();
    }
}
