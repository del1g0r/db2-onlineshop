package com.study.onlineshop.dao.jdbc.mapper;

import com.study.onlineshop.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("creation_date").toLocalDateTime(),
                resultSet.getDouble("price"));
    }
}