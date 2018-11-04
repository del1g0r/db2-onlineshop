package com.study.onlineshop.entity;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private LocalDateTime creationDate;
    private double price;

    public Product(int id, String name, LocalDateTime creationDate, double price) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.price = price;
    }

    public Product(String name, LocalDateTime creationDate, double price) {
        this(0, name, creationDate, price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", price=" + price +
                '}';
    }
}
