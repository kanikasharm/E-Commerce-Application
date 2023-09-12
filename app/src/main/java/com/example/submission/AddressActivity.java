package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.submission.adapters.AddressAdapter;
import com.example.submission.models.AddressModel;
import com.example.submission.models.ShowAllModel;
import com.example.submission.models.newProductsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
 Button addAddress;
 RecyclerView recyclerView;
 AddressAdapter addressAdapter;
 List<AddressModel> addressModelList;
 Button payment;
 FirebaseFirestore firestore;
 FirebaseAuth auth;
 androidx.appcompat.widget.Toolbar toolbar;
 String mAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        payment = findViewById(R.id.payment_btn);
        addAddress = findViewById(R.id.add_address_btn);
        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Object obj = getIntent().getSerializableExtra("item");

        recyclerView = findViewById(R.id.address_rec);
        addressModelList = new ArrayList<>();
        addressAdapter = new AddressAdapter(AddressActivity.this, addressModelList, this);
        recyclerView.setAdapter(addressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).
                collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AddressModel addressModel = document.toObject(AddressModel.class);
                                addressModelList.add(addressModel);
                                addressAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(AddressActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amount = 0.0;
                if(obj instanceof com.example.submission.models.newProductsList) {
                    com.example.submission.models.newProductsList newProductsList = (com.example.submission.models.newProductsList) obj;
                    amount = newProductsList.getPrice();
                }
                if(obj instanceof com.example.submission.models.PopularProductsModel) {
                    com.example.submission.models.PopularProductsModel popularProductsModel  = (com.example.submission.models.PopularProductsModel) obj;
                    amount = popularProductsModel.getPrice();
                }
                if(obj instanceof com.example.submission.models.ShowAllModel) {
                    com.example.submission.models.ShowAllModel showAllModel = (com.example.submission.models.ShowAllModel) obj;
                    amount = showAllModel.getPrice();
                }
                Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);
                intent.putExtra("amount", amount);
                startActivity(intent);
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });
    }

    @Override
    public void setAddress(String address) {
      mAddress = address;
    }
}