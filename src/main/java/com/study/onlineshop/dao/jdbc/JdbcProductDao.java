package com.study.onlineshop.dao.jdbc;

import com.study.onlineshop.dao.ProductDao;
import com.study.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.study.onlineshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private static final String GET_ALL_SQL = "SELECT id, name, creation_date, price FROM product";
    private static final String CREATE_SQL = "INSERT INTO product(name, creation_date, price) VALUES (?, ?, ?)";
    private static final String GET_SQL = "SELECT id, name, creation_date, price FROM product WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE product SET name=?, creation_date=?, price=? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM product WHERE id = ?";
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> getAll() {
        List<Product> products = jdbcTemplate.query(GET_ALL_SQL, PRODUCT_ROW_MAPPER);
        return products;
    }

    @Override
    public void create(Product product) {
        jdbcTemplate.update(
                CREATE_SQL,
                product.getName(), Timestamp.valueOf(product.getCreationDate()), product.getPrice());
    }

    @Override
    public Product get(int id) {
        Product product = jdbcTemplate.queryForObject(GET_SQL, new Object[]{id}, PRODUCT_ROW_MAPPER);
        return product;
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(
                UPDATE_SQL,
                product.getName(), Timestamp.valueOf(product.getCreationDate()), product.getPrice(), product.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(
                DELETE_SQL,
                id);
    }
}