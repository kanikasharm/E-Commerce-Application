package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.submission.adapters.ShowAllAdapter;
import com.example.submission.models.ShowAllModel;
import com.example.submission.models.newProductsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ShowAllModel> showAllModellist;
    ShowAllAdapter showAllAdapter;
    FirebaseFirestore db;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
//        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.show_all_rec);
        toolbar = findViewById(R.id.show_all_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String type = getIntent().getStringExtra("type");


        db = FirebaseFirestore.getInstance();
        showAllModellist = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(ShowAllActivity.this, showAllModellist);
        recyclerView.setAdapter(showAllAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(ShowAllActivity.this, 2));

        if (type == null) {
            db.collection("ShowAll")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModellist.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Toast.makeText(ShowAllActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("Men")) {
            db.collection("ShowAll").whereEqualTo("type", "Men")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModellist.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Toast.makeText(ShowAllActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("Watch")) {
            db.collection("ShowAll").whereEqualTo("type", "Watch")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModellist.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Toast.makeText(ShowAllActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("Kids")) {
            db.collection("ShowAll").whereEqualTo("type", "Kids")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModellist.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Toast.makeText(ShowAllActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("Dress")) {
            db.collection("ShowAll").whereEqualTo("type", "Dress")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModellist.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Toast.makeText(ShowAllActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("Shoes")) {
            db.collection("ShowAll").whereEqualTo("type", "Shoes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModellist.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Toast.makeText(ShowAllActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("Camera")) {
            db.collection("ShowAll").whereEqualTo("type", "Camera")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModellist.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Toast.makeText(ShowAllActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}