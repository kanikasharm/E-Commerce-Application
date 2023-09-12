package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.submission.models.PopularProductsModel;
import com.example.submission.models.ShowAllModel;
import com.example.submission.models.newProductsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailed_img;
    TextView detailed_name, rating, detailed_desc, detailed_price, quantity;
    Button add_to_cart, buy_now;
    ImageView addItems, removeItems;
    Toolbar toolbar;

    newProductsList newProductsList = null;
    PopularProductsModel popularProductsModel = null;
    ShowAllModel showAllModel = null;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    int totalQuantity = 1;
    int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
//        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detailed");
        if (object instanceof newProductsList) {
            newProductsList = (newProductsList) object;
        } else if (object instanceof PopularProductsModel) {
            popularProductsModel = (PopularProductsModel) object;
        } else if (object instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) object;
        }


        detailed_img = findViewById(R.id.detailed_img);
        detailed_name = findViewById(R.id.detailed_name);
        detailed_desc = findViewById(R.id.detailed_desc);
        detailed_price = findViewById(R.id.detailed_price);
        quantity = findViewById(R.id.quantity);
        rating = findViewById(R.id.rating);
        add_to_cart = findViewById(R.id.add_to_cart);
        buy_now = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.plus_icon);
        removeItems = findViewById(R.id.minus_icon);
        toolbar = findViewById(R.id.detailed_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //new producs
        if (newProductsList != null) {
            Glide.with(getApplicationContext()).load(newProductsList.getImg_url()).into(detailed_img);
            detailed_name.setText(newProductsList.getName());
            detailed_desc.setText(newProductsList.getDescription());
            detailed_price.setText(String.valueOf(newProductsList.getPrice()));
            rating.setText(newProductsList.getRating());

            totalPrice = newProductsList.getPrice() * totalQuantity;
        }

        //popular products

        if (popularProductsModel != null) {
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailed_img);
            detailed_name.setText(popularProductsModel.getName());
            detailed_desc.setText(popularProductsModel.getDescription());
            detailed_price.setText(String.valueOf(popularProductsModel.getPrice()));
            rating.setText(popularProductsModel.getRating());

            totalPrice = popularProductsModel.getPrice() * totalQuantity;
        }

        //show all
        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailed_img);
            detailed_name.setText(showAllModel.getName());
            detailed_desc.setText(showAllModel.getDescription());
            detailed_price.setText(String.valueOf(showAllModel.getPrice()));
            rating.setText(showAllModel.getRating());

            totalPrice = showAllModel.getPrice() * totalQuantity;
        }
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductsList != null) {
                        totalPrice = newProductsList.getPrice() * totalQuantity;
                    }
                    if (popularProductsModel != null) {
                        totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    }
                    if (showAllModel != null) {
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailedActivity.this, AddressActivity.class));
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

            private void addToCart() {
                String saveCurrentTime, savecCurrentDate;

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currDate = new SimpleDateFormat("MM dd, yyyy");
                savecCurrentDate = currDate.format(calForDate.getTime());

                SimpleDateFormat currTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currTime.format(calForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", detailed_name.getText().toString());
                cartMap.put("productPrice", detailed_price.getText().toString());
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("currentDate", savecCurrentDate);
                cartMap.put("totalQuantity", quantity.getText().toString());
                cartMap.put("totalPrice", totalPrice);

                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).
                        collection("Users").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(DetailedActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
}


