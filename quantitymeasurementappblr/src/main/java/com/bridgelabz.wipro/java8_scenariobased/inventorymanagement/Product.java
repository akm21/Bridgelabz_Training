package com.bridgelabz.wipro.java8_scenariobased.inventorymanagement;

public class Product {
    String productId;
    String Category;
    int quantity;
    double price;

    public Product(String productId, String category, int quantity, double price) {
        this.productId = productId;
        Category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getCategory() {
        return Category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", Category='" + Category + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
