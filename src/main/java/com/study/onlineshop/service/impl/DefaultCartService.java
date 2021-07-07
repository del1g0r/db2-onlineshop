package com.study.onlineshop.service.impl;

import com.study.onlineshop.entity.Product;
import com.study.onlineshop.entity.Purchase;
import com.study.onlineshop.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCartService implements CartService {

    @Override
    public void add(List<Purchase> purchases, Product product) {
        for (Purchase purchase : purchases) {
            if (purchase.getProduct().getId() == product.getId()) {
                purchase.setCount(purchase.getCount() + 1);
                purchase.setSum(purchase.getSum() + product.getPrice());
                return;
            }
        }
        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setCount(1);
        purchase.setSum(product.getPrice());
        purchases.add(purchase);
    }

    @Override
    public void delete(List<Purchase> purchases, Product product) {
        for (Purchase purchase : purchases) {
            if (purchase.getProduct().getId() == product.getId()) {
                if (purchase.getCount() == 1) {
                    purchases.remove(purchase);
                    return;
                } else {
                    purchase.setCount(purchase.getCount() - 1);
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
