package ru.mirea.androidcoursework.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CardProduct implements Parcelable
{
    private String id;
    private String title;
    private String description;
    private String category;
    private double price;
    private String imageUrl;



    public CardProduct(String id, String imageUrl,  String title, String description, String category ,double price)
    {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public CardProduct() {}

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    protected CardProduct(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        category = in.readString();
        price = in.readDouble();
        imageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeString(imageUrl);
    }

    public static final Creator<CardProduct> CREATOR = new Creator<CardProduct>() {
        @Override
        public CardProduct createFromParcel(Parcel in) {
            return new CardProduct(in);
        }

        @Override
        public CardProduct[] newArray(int size) {
            return new CardProduct[size];
        }
    };
}
