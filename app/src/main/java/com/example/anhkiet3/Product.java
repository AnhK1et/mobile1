package com.example.anhkiet3;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String price;
    private int imageResId;
    private String category;
    private int quantity = 1;
    private String oldPrice;
    private int promoIndex = -1;
    private boolean selected = false;

    public Product(String name, String price, int imageResId, String category) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.category = category;
        this.quantity = 1;
        this.promoIndex = -1;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getPromoIndex() {
        return promoIndex;
    }

    public void setPromoIndex(int promoIndex) {
        this.promoIndex = promoIndex;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
} 