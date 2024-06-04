package ru.mirea.androidcoursework.instance;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.androidcoursework.entity.CardProductInCart;

public class ProductsInCart
{
    private ProductsInCart() {}
    private static ProductsInCart instance;
    public static ProductsInCart getInstance()
    {
        if (instance == null)
        {
            instance = new ProductsInCart();
        }
        return instance;
    }

    private List<CardProductInCart> productsInCart = new ArrayList<>();

    public List<CardProductInCart> getProductsInCart() {
        return productsInCart;
    }

    public void addProductInCart(CardProductInCart cardProductInCart) {productsInCart.add(cardProductInCart);}

    public void removeProductInCart(CardProductInCart cardProductInCart) {productsInCart.remove(cardProductInCart);}

    public void clearProductsInCart()
    {
        productsInCart.clear();
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (CardProductInCart product : productsInCart) {
            totalPrice += product.getTotalPrice();
        }
        return totalPrice;
    }
}
