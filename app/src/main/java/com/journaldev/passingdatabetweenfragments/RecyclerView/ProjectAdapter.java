package com.journaldev.passingdatabetweenfragments.RecyclerView;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.journaldev.passingdatabetweenfragments.Model.Product;
import com.journaldev.passingdatabetweenfragments.R;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private List<Product> itemsData;

    OnItemClickListener mItemClickListener;

    public ProjectAdapter(List<Product> itemsData) {
        this.itemsData = itemsData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new views
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_view, parent, false);

        // create ViewHolder
        ViewHolder vh = new ViewHolder(itemLayoutView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = itemsData.get(position);

        final String kod1C = product.getId();
        final String name = product.getName();
        final String model = product.getModel();
        final String ostatok = product.getOstatok();


        holder.titleText.setText(name);
        holder.modelText.setText(model);
        holder.kodText.setText(kod1C);
        holder.priceText.setText(ostatok);
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleText;
        public TextView modelText;
        public TextView kodText;
        public TextView priceText;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            titleText = (TextView) itemView.findViewById(R.id.title);
            modelText = (TextView) itemView.findViewById(R.id.model);
            kodText = (TextView) itemView.findViewById(R.id.kod1C);
            priceText = (TextView) itemView.findViewById(R.id.price);

            itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition()); //OnItemClickListener mItemClickListener;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}