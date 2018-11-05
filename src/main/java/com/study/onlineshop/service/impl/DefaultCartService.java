package com.study.onlineshop.service.impl;

import com.study.onlineshop.entity.Product;
import com.study.onlineshop.entity.Purchase;
import com.study.onlineshop.service.CartService;

import java.util.List;

public class DefaultCartService implements CartService {

    @Override
    public void add(List<Purchase> purchases, Product product) {
        for (Purchase purchase : purchases) {
            if (purchase.getProduct().getId() == product.getId()) {
                purchase.setCnt(purchase.getCnt() + 1);
                purchase.setSum(purchase.getSum() + product.getPrice());
                return;
            }
        }
        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setCnt(1);
        purchase.setSum(product.getPrice());
        purchases.add(purchase);
    }

    @Override
    public void delete(List<Purchase> purchases, Product product) {
        for (Purchase purchase : purchases) {
            if (purchase.getProduct().getId() == product.getId()) {
                if (purchase.getCnt() == 1) {
                    purchases.remove(purchase);
                    return;
                } else {
                    purchase.setCnt(purchase.getCnt() - 1);
                    purchase.setSum(purchase.getSum() - product.getPrice());
                }
            }
        }
    }

    @Override
    public void clear(List<Purchase> purchases) {
        purchases.clear();
    }
}
