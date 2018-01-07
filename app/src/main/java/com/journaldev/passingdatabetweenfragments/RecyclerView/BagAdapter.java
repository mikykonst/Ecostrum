package com.journaldev.passingdatabetweenfragments.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.journaldev.passingdatabetweenfragments.Model.Product;
import com.journaldev.passingdatabetweenfragments.R;

import java.util.List;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ViewHolder> {

    private List<Product> itemsData;

    public BagAdapter(List<Product> itemsData) {
        this.itemsData = itemsData;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bag_list_view, parent, false);
        ViewHolder vh = new ViewHolder(itemLayoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = itemsData.get(position);

        String name = product.getName();
        String model = product.getModel();
        String count = product.getChoosedCount();


        holder.titleText.setText(name);
        holder.modelText.setText(model);
        holder.countText.setText(count + " шт.");
        holder.pricePerEach.setText(product.getmPrice() + " грн.");

    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleText;
        public TextView modelText;
        public TextView countText;
        public TextView pricePerEach;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            titleText = (TextView) itemView.findViewById(R.id.bag_name);
            modelText = (TextView) itemView.findViewById(R.id.bag_model);
            countText = (TextView) itemView.findViewById(R.id.bag_count);
            pricePerEach = (TextView) itemView.findViewById(R.id.price_per_one);
        }
    }
}
