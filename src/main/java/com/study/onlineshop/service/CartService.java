package com.study.onlineshop.service;

import com.study.onlineshop.entity.Product;
import com.study.onlineshop.entity.Purchase;

import java.util.List;

public interface CartService {

    void add(List<Purchase> purchases, Product product);

    void delete(List<Purchase> purchases, Product product);

    void clear(List<Purchase> purchases);
}
