package com.study.onlineshop.dao.jdbc;

import com.study.onlineshop.dao.ProductDao;
import com.study.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.study.onlineshop.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private static final String GET_ALL_SQL = "SELECT id, name, creation_date, price FROM product;";
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    @Override
    public List<Product> getAll() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SQL)) {


            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/db2_onlineshop";
        String name = "postgres";
        String password = "root";

        return DriverManager.getConnection(url, name, password);
    }
}
