package com.example.submission.models;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularProductsModel {
    String img_url, name, type;
    int price;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PopularProductsModel() {
    }

    public PopularProductsModel(String img_url, String name, String type, int price) {
        this.img_url = img_url;
        this.name = name;
        this.type = type;
        this.price = price;

}
}
