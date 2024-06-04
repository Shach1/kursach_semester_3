package ru.mirea.androidcoursework.entity;

public class CardProductInCart
{
    private CardProduct cardProduct;
    private int amount;
    private double totalPrice;

    public CardProductInCart(CardProduct cardProduct)
    {
        this.cardProduct = cardProduct;
        this.amount = 1;
        this.totalPrice = cardProduct.getPrice();
    }

    public CardProduct getCardProduct() {
        return cardProduct;
    }

    public void setCardProduct(CardProduct cardProduct) {
        this.cardProduct = cardProduct;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
