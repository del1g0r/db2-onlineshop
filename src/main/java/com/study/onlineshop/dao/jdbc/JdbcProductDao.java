package com.study.onlineshop.dao.jdbc;

import com.study.onlineshop.dao.ProductDao;
import com.study.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.study.onlineshop.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcProductDao implements ProductDao {

    private static final String GET_ALL_SQL = "SELECT id, name, creation_date, price FROM product";
    private static final String CREATE_SQL = "INSERT INTO product(name, creation_date, price) VALUES (?, ?, ?)";
    private static final String GET_SQL = "SELECT id, name, creation_date, price FROM product WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE product SET name=?, creation_date=?, price=? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM product WHERE id = ?";
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private Properties properties;

    public JdbcProductDao(Properties properties) {
        this.properties = properties;
    }

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

    @Override
    public void create(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_SQL);) {
            statement.setString(1, product.getName());
            statement.setTimestamp(2, Timestamp.valueOf(product.getCreationDate()));
            statement.setDouble(3, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement prepareGetStatement(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_SQL);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public Product get(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = prepareGetStatement(connection, id);
             ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                return product;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);) {
            statement.setString(1, product.getName());
            statement.setTimestamp(2, Timestamp.valueOf(product.getCreationDate()));
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id)  {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        String url = properties.getProperty("url");
        String name = properties.getProperty("name");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, name, password);
    }
}
