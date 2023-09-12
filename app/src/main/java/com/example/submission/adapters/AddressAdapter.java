package com.example.submission.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission.R;
import com.example.submission.models.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    List<AddressModel> list;
    SelectedAddress selectedAddress;

    private RadioButton selectedRadioBtn;

    public AddressAdapter(Context context, List<AddressModel> list, SelectedAddress selectedAddress) {
        this.context = context;
        this.list = list;
        this.selectedAddress = selectedAddress;
    }

    public AddressAdapter() {
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
      holder.userAddress.setText(list.get(position).getUserAddress());
      holder.radioButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              for (AddressModel addressModel : list) {
                  addressModel.setSelected(false);
              }
              list.get(position).setSelected(true);

              if (selectedRadioBtn != null) {
                  selectedRadioBtn.setChecked(false);
              }
              selectedRadioBtn = (RadioButton) v;
              selectedRadioBtn.setChecked(true);
              selectedAddress.setAddress(list.get(position).getUserAddress());
          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userAddress;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userAddress = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);

        }
    }
    public interface SelectedAddress {
        void setAddress(String address);
    }
}

