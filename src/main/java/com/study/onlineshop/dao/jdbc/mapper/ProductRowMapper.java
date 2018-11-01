package com.study.onlineshop.dao.jdbc.mapper;

import com.study.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductRowMapper {
    // id, name, creationDate, price
    public Product mapRow(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("creation_date").toLocalDateTime(),
                resultSet.getDouble("price")
        );
    }
}
