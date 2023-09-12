package com.example.submission.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission.DetailedActivity;
import com.example.submission.R;
import com.example.submission.models.MyCartModel;

import org.w3c.dom.Text;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> list;
    int totalAmount= 0;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    public MyCartAdapter() {
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.currTime.setText(list.get(position).getCurrentTime());
        holder.currDate.setText(list.get(position).getCurrentDate());
        holder.productName.setText(list.get(position).getProductName());
        holder.productPrice.setText("$" + list.get(position).getProductPrice());
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());
        holder.totalPrice.setText("$" + list.get(position).getTotalPrice());

        totalAmount = totalAmount + list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView currTime;
        TextView currDate;
        TextView productName;
        TextView productPrice;
        TextView totalQuantity;
        TextView totalPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currTime = itemView.findViewById(R.id.current_time);
            currDate = itemView.findViewById(R.id.current_date);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}


