package com.example.submission.fragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.submission.R;
import com.example.submission.adapters.CategoryAdapter;
import com.example.submission.adapters.NewPoductsAdapter;
import com.example.submission.adapters.PopularProductsAdapter;
import com.example.submission.models.CategoryModel;
import com.example.submission.models.PopularProductsModel;
import com.example.submission.models.newProductsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    RecyclerView catRecyclerView;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;
    NewPoductsAdapter newPoductsAdapter;
    List<newProductsList> newProductsLists;
    RecyclerView prodRecyclerView, popularProdRecyclerView;

    ProgressDialog pd;
    LinearLayout linearLayout;

    //popular products
    List<PopularProductsModel> popularProductsModelList;
    PopularProductsAdapter popularProductsAdapter;


    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("StringFormatInvalid")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_home, container, false);
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        catRecyclerView = root.findViewById(R.id.rec_category);
        prodRecyclerView = root.findViewById(R.id.new_product_rec);
        popularProdRecyclerView = root.findViewById(R.id.popular_rec);
        pd = new ProgressDialog(getActivity());
        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);
        db = FirebaseFirestore.getInstance();
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.shoes, "Discount on shoes", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.cosmetic, "Discount on cosmetics", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.sixty, "60% OFF", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);
        pd.setTitle("Welcome to E-Commerce");
        pd.setMessage("Please wait..");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<CategoryModel>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);
        try {
            db.collection("category").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(
                    new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore error", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    categoryModelList.add(dc.getDocument().toObject(CategoryModel.class));
                                }
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                pd.dismiss();
                            }
                        }
                    });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        newProductsLists = new ArrayList<>();
        newPoductsAdapter = new NewPoductsAdapter(getContext(), newProductsLists);
        prodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        prodRecyclerView.setAdapter(newPoductsAdapter);


        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                newProductsList newProductsList = document.toObject(newProductsList.class);
                                newProductsLists.add(newProductsList);
                                newPoductsAdapter.notifyDataSetChanged();
                            }

                        }
                        else{
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        popularProdRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        popularProdRecyclerView.setAdapter(popularProductsAdapter);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged();
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }
}