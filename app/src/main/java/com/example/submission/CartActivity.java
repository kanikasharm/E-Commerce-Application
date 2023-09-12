package com.example.submission;

import static com.example.submission.R.id.overallPrice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.submission.adapters.MyCartAdapter;
import com.example.submission.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartRecyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> myCartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView overAllAmount;
    int overAllTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartRecyclerView = findViewById(R.id.cart_rec);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.my_cart_toolbar);
        overAllAmount = findViewById(R.id.overallPrice);
        LocalBroadcastManager.getInstance(CartActivity.this).registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(CartActivity.this, myCartModelList);
        cartRecyclerView.setAdapter(myCartAdapter);
        cartRecyclerView.setNestedScrollingEnabled(false);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("Users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MyCartModel myCartModel = document.toObject(MyCartModel.class);
                                myCartModelList.add(myCartModel);
                                myCartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int billing = intent.getIntExtra("totalAmount", 0);
            overAllAmount.setText("Total Amount " + "$" + billing);
        }
    };
}

