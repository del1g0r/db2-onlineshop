package com.study.onlineshop.dao;

import com.study.onlineshop.entity.Product;

import javax.servlet.ServletException;
import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    void create(Product product) throws ServletException;

    Product get(int id) throws ServletException;

    void update(Product product) throws ServletException;

    void delete(int id) throws ServletException;
}
