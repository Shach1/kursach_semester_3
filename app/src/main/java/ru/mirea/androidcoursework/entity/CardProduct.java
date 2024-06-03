package ru.mirea.androidcoursework.entity;

public class CardProduct
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
}
