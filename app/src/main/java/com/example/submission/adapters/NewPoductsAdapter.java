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
import com.example.submission.models.newProductsList;

import java.util.List;

public class NewPoductsAdapter extends RecyclerView.Adapter<NewPoductsAdapter.ViewHolder> {
    Context context;
    List<newProductsList> list;

    public NewPoductsAdapter(Context context, List<newProductsList> list) {
        this.context = context;
        this.list = list;
    }

    public NewPoductsAdapter() {
    }

    @NonNull
    @Override
    public NewPoductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewPoductsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products, parent, false));
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.new_products, parent, false);
//
//        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull NewPoductsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.new_img);
        holder.new_name.setText(list.get(position).getName());
        holder.new_price.setText(String.valueOf(list.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView new_img;
        TextView new_name, new_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            new_img = itemView.findViewById(R.id.pop_img);
            new_name = itemView.findViewById(R.id.new_product_name);
            new_price = itemView.findViewById(R.id.pop_price);
        }
    }
}
