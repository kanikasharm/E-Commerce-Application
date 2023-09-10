package com.example.submission.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission.R;
import com.example.submission.models.PopularProductsModel;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {
    Context context;
    List<PopularProductsModel> list2;

    public PopularProductsAdapter() {
    }

    public PopularProductsAdapter(Context context, List<PopularProductsModel> list) {
        this.context = context;
        this.list2 = list;
    }

    @NonNull
    @Override
    public PopularProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list2.get(position).getImg_url()).into(holder.pop_img);
        holder.pop_name.setText(list2.get(position).getName());
        holder.pop_price.setText(String.valueOf(list2.get(position).getPrice()));

    }


    @Override
    public int getItemCount() {
        return list2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
          ImageView pop_img;
          TextView pop_name, pop_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pop_img = itemView.findViewById(R.id.pop_img);
            pop_name = itemView.findViewById(R.id.pop_product_name);
            pop_price = itemView.findViewById(R.id.pop_price);
        }
    }
}
