package com.example.anhkiet3;
import java.io.Serializable;
import java.util.List;
public class Order implements Serializable {
    private int id;
    private String date;
    private double total;
    private List<String> products; // Tên sản phẩm đơn giản
    public Order(int id, String date, double total, List<String> products) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.products = products;
    }
    public int getId() { return id; }
    public String getDate() { return date; }
    public double getTotal() { return total; }
    public List<String> getProducts() { return products; }
} 