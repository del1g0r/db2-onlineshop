package com.study.onlineshop.service;

import com.study.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    void create(Product product);

    Product get(int id);

    void update(Product product);

    void delete(int id);
}
