package com.example.anhkiet3;

import java.io.Serializable;

public class Product implements Serializable {
    // Trường cũ
    private String name;
    private String price;
    private int imageResId;
    private String category;
    private int quantity = 1;
    private String oldPrice;
    private int promoIndex = -1;
    private boolean selected = false;
    private String description;
    // Trường mới từ FakeStoreAPI
    private int id;
    private String title;
    private String image;
    private double priceDouble;

    public Product() {}

    public Product(String name, String price, int imageResId, String category, String description) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.category = category;
        this.description = description;
        this.quantity = 1;
        this.promoIndex = -1;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getOldPrice() { return oldPrice; }
    public void setOldPrice(String oldPrice) { this.oldPrice = oldPrice; }
    public int getPromoIndex() { return promoIndex; }
    public void setPromoIndex(int promoIndex) { this.promoIndex = promoIndex; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public double getPriceDouble() { return priceDouble; }
    public void setPriceDouble(double priceDouble) { this.priceDouble = priceDouble; }
} 