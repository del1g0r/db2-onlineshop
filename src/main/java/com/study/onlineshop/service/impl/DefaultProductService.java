package com.study.onlineshop.service.impl;

import com.study.onlineshop.dao.ProductDao;
import com.study.onlineshop.entity.Product;
import com.study.onlineshop.service.ProductService;

import java.util.List;

public class DefaultProductService implements ProductService {

    private ProductDao productDao;

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public void create(Product product) {
        productDao.create(product);
    }

    @Override
    public Product get(int id) {
        return productDao.get(id);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void delete(int id) {
        productDao.delete(id);
    }
}
